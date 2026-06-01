export interface ChatRequest {
  message: string
}

export interface ChatResponse {
  response: string
  sessionId: number
}