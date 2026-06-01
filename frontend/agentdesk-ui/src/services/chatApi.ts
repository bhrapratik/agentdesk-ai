import axios from "axios"

const api = axios.create({
  baseURL: "http://localhost:8080/api"
})

export async function sendMessage(
  message: string,
  sessionId?: number
) {
  const response = await api.post(
    "/chat",
    {
      message,
      sessionId
    }
  )

  return response.data
}