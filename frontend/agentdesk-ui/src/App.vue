<script setup lang="ts">
import { ref, nextTick, onMounted } from "vue";
import { sendMessage } from "./services/chatApi";
import type { ChatMessage } from "./components/ChatMessage";
import {
  getDocuments,
  uploadDocument,
  deleteDocument,
} from "./services/knowledgeApi";
import type { KnowledgeDocument } from "./types/knowledge";

const question = ref("");
const loading = ref(false);
const showKnowledge = ref(false);
const sessionId = ref<number>();

const messages = ref<ChatMessage[]>([
  {
    role: "assistant",
    content: "Hello! How can I help you today?",
  },
]);
const documents = ref<KnowledgeDocument[]>([]);

const selectedFiles = ref<File | null>(null);

const onFileSelected = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files && target.files.length > 0) {
    selectedFiles.value = target.files[0];
  }
};

const upload = async () => {
  if (!selectedFiles.value) {
    return;
  }

  const formData = new FormData();
  formData.append("file", selectedFiles.value);

  try {
    await uploadDocument(selectedFiles.value);
    documents.value = await getDocuments();
    selectedFiles.value = null;
  } catch (error) {
    console.error("Error uploading document:", error);
    alert("Failed to upload document.");
  }
};

const deleteDocumentFile = async (docId: number) => {
  try {
    await deleteDocument(docId);
    documents.value = documents.value.filter((doc) => doc.id !== docId);
  } catch (error) {
    console.error("Error deleting document:", error);
    alert("Failed to delete document.");
  }
};

const ask = async () => {
  if (!question.value.trim()) {
    return;
  }

  const userMessage = question.value;

  messages.value.push({
    role: "user",
    content: userMessage,
  });

  question.value = "";

  loading.value = true;

  try {
    const result = await sendMessage(userMessage, sessionId.value);

    sessionId.value = result.sessionId;
    messages.value.push({
      role: "assistant",
      content: result.response,
      sources: result.sources,
    });
  } catch {
    messages.value.push({
      role: "assistant",
      content: "Failed to connect to backend.",
    });
  } finally {
    loading.value = false;

    await nextTick();

    window.scrollTo(0, document.body.scrollHeight);
  }
};

onMounted(async () => {
  const docs = await getDocuments();
  documents.value = docs;
  console.log("Documents in knowledge base:", docs);
});
</script>

<template>
  <div class="chat-container">
    <div class="header">🤖 AgentDesk AI Assistant</div>

    <div class="messages">
      <div
        v-for="(message, index) in messages"
        :key="index"
        class="message-row"
        :class="message.role"
      >
        <div class="message-bubble" :class="message.role">
          {{ message.content }}

          <div
            v-if="message.sources && message.sources.length > 0"
            class="sources"
          >
            <div class="sources-title">Sources</div>

            <div
              v-for="source in message.sources"
              :key="source"
              class="source-chip"
            >
              📄 {{ source }}
            </div>
          </div>
        </div>
      </div>

      <div v-if="loading" class="message-row assistant">
        <div class="message-bubble assistant">Thinking...</div>
      </div>
    </div>

    <div class="input-area">
      <input
        v-model="question"
        @keyup.enter="ask"
        placeholder="Ask something..."
      />

      <button @click="ask" :disabled="loading">Send</button>
    </div>
  </div>
  <Button class="knowledge-fab" @click="showKnowledge = !showKnowledge">
    📚
  </Button>
  <div v-if="showKnowledge" class="modal-overlay">
    <div class="modal">
      <div class="modal-header">
        <h2>Knowledge Base</h2>

        <button class="close-btn" @click="showKnowledge = false">✕</button>
      </div>

      <div class="modal-content">
        <input type="file" @change="onFileSelected" />

        <button @click="upload">Upload</button>

        <hr />

        <h3>Documents</h3>

        <ul>
          <li v-for="doc in documents" :key="doc.id"  class="document-row">
            <span> 📄 {{ doc.title }} </span>
            <button class="delete-btn" @click="deleteDocumentFile(doc.id)">Delete</button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style>
body {
  margin: 0;
  font-family: Inter, Arial, sans-serif;
  background: #0f172a;
  color: #e2e8f0;
}

.chat-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #0f172a;
}

.header {
  padding: 18px 24px;
  font-size: 22px;
  font-weight: 700;
  background: #111827;
  border-bottom: 1px solid #1f2937;
  color: #f8fafc;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
}

.message-row {
  display: flex;
  margin-bottom: 16px;
}

.message-row.user {
  justify-content: flex-end;
}

.message-row.assistant {
  justify-content: flex-start;
}

.message-bubble {
  max-width: 75%;
  padding: 14px 18px;
  border-radius: 18px;
  white-space: pre-wrap;
  line-height: 1.5;
}

.message-bubble.user {
  background: #2563eb;
  color: white;
}

.message-bubble.assistant {
  background: #1e293b;
  color: #e2e8f0;
  border: 1px solid #334155;
}

.input-area {
  display: flex;
  gap: 12px;
  padding: 20px;
  background: #111827;
  border-top: 1px solid #1f2937;
}

.input-area input {
  flex: 1;
  padding: 14px;
  border-radius: 14px;
  border: 1px solid #334155;
  background: #1e293b;
  color: white;
  font-size: 15px;
}

.input-area input::placeholder {
  color: #94a3b8;
}

.input-area input:focus {
  outline: none;
  border-color: #2563eb;
}

.input-area button {
  padding: 14px 24px;
  border-radius: 14px;
  border: none;
  background: #2563eb;
  color: white;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
}

.input-area button:hover {
  background: #1d4ed8;
}

.input-area button:disabled {
  opacity: 0.5;
}

.knowledge-fab {
  position: fixed;
  bottom: 90px;
  right: 24px;

  width: 60px;
  height: 60px;

  border-radius: 50%;

  border: none;

  background: #2563eb;

  color: white;

  font-size: 24px;

  cursor: pointer;

  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.35);
}

.modal-overlay {
  position: fixed;
  inset: 0;

  background: rgba(0, 0, 0, 0.7);

  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  width: 700px;
  max-width: 90%;

  background: #111827;

  border: 1px solid #334155;

  border-radius: 16px;

  color: white;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 20px;

  border-bottom: 1px solid #334155;
}

.modal-content {
  padding: 20px;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
}

.document-row {
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 10px;

  border-bottom:
    1px solid #334155;
}

.delete-btn {
  border: none;
  background: transparent;

  cursor: pointer;

  font-size: 18px;
}
</style>
