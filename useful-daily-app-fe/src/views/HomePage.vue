<template>
  <div class="home-page">
    <h1>每日打卡系統</h1>
    
    <div class="status-card">
      <h2>今日狀態</h2>
      <div class="status-display">
        <span class="status-text" :class="{ completed: isCheckedIn }">
          {{ isCheckedIn ? '✅ 已完成' : '❌ 未完成' }}
        </span>
      </div>
    </div>

    <div class="action-card">
      <button 
        @click="handleCheckIn" 
        :disabled="loading || isCheckedIn"
        class="checkin-btn"
        :class="{ completed: isCheckedIn }"
      >
        {{ loading ? '處理中...' : (isCheckedIn ? '今日已打卡' : '今日打卡') }}
      </button>
    </div>

    <div v-if="message" class="message" :class="messageType">
      {{ message }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { checkInService } from '../services/ApiService.js'

const isCheckedIn = ref(false)
const loading = ref(false)
const message = ref('')
const messageType = ref('')

const showMessage = (text, type = 'info') => {
  message.value = text
  messageType.value = type
  setTimeout(() => {
    message.value = ''
  }, 3000)
}

const loadTodayStatus = async () => {
  try {
    loading.value = true
    // 根據後端回應判斷狀態
    isCheckedIn.value = await checkInService.getTodayStatus()
  } catch (error) {
    showMessage('載入狀態失敗', 'error')
    console.error('載入狀態錯誤:', error)
  } finally {
    loading.value = false
  }
}

const handleCheckIn = async () => {
  if (isCheckedIn.value) return
  
  try {
    loading.value = true
    const response = await checkInService.checkInToday()
    
    if (response) {
      isCheckedIn.value = true
      showMessage('打卡成功！', 'success')
    }
  } catch (error) {
    showMessage('打卡失敗，請稍後再試', 'error')
    console.error('打卡錯誤:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadTodayStatus()
})
</script>

<style scoped>
.home-page {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.status-card, .action-card {
  background: #f5f5f5;
  border-radius: 8px;
  padding: 20px;
  margin: 20px 0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.status-text {
  font-size: 1.5em;
  font-weight: bold;
  color: #e74c3c;
}

.status-text.completed {
  color: #27ae60;
}

.checkin-btn {
  background: #3498db;
  color: white;
  border: none;
  padding: 15px 30px;
  font-size: 1.2em;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s;
}

.checkin-btn:hover:not(:disabled) {
  background: #2980b9;
}

.checkin-btn:disabled {
  background: #bdc3c7;
  cursor: not-allowed;
}

.checkin-btn.completed {
  background: #27ae60;
}

.message {
  padding: 10px;
  border-radius: 5px;
  margin-top: 20px;
}

.message.success {
  background: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.message.error {
  background: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}

.message.info {
  background: #d1ecf1;
  color: #0c5460;
  border: 1px solid #bee5eb;
}
</style>
