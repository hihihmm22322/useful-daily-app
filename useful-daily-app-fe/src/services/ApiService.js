import axios from 'axios'

const API_BASE_URL = '/api' // 使用相對路徑，透過 nginx 代理

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const checkInService = {
  // 查看今日狀態
  async getTodayStatus() {
    const response = await apiClient.get('/checkin/today')
    console.log("response : %o", response);
    return response.data.success ? response.data.data : false
  },
  
  // 今日打卡
  async checkInToday() {
    const response = await apiClient.post('/checkin/today')
    return response.data.success ? response.data.data : false
  }
}