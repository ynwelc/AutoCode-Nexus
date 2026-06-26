<template>
  <div id="userLoginPage">
    <div class="login-container">
      <div class="login-card">
        <div class="login-header">
          <div class="logo-wrapper">
            <img :src="logoImg" alt="Logo" class="logo-image" />
          </div>
          <h2 class="title">欢迎回来</h2>
          <p class="desc">登录 AutoCode Nexus，开始你的创作之旅</p>
        </div>
        <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit" class="login-form">
          <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入账号' }]">
            <a-input v-model:value="formState.userAccount" placeholder="请输入账号" size="large" class="form-input" />
          </a-form-item>
          <a-form-item
            name="userPassword"
            :rules="[
              { required: true, message: '请输入密码' },
              { min: 8, message: '密码长度不能小于 8 位' },
            ]"
          >
            <a-input-password v-model:value="formState.userPassword" placeholder="请输入密码" size="large" class="form-input" />
          </a-form-item>
          <div class="tips">
            没有账号？
            <RouterLink to="/user/register" class="link">立即注册</RouterLink>
          </div>
          <a-form-item>
            <a-button type="primary" html-type="submit" size="large" class="submit-btn">登录</a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { userLogin } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import logoImg from '@/assets/logo.png'

const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
})

const router = useRouter()
const loginUserStore = useLoginUserStore()

const handleSubmit = async (values: any) => {
  const res = await userLogin(values)
  if (res.data.code === 0 && res.data.data) {
    await loginUserStore.fetchLoginUser()
    message.success('登录成功')
    router.push({
      path: '/',
      replace: true,
    })
  } else {
    message.error('登录失败，' + res.data.message)
  }
}
</script>

<style scoped>
#userLoginPage {
  min-height: 100vh;
  background:
    linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%),
    radial-gradient(ellipse at 20% 20%, rgba(99, 102, 241, 0.08) 0%, transparent 50%),
    radial-gradient(ellipse at 80% 80%, rgba(167, 139, 250, 0.08) 0%, transparent 50%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.login-container {
  position: relative;
  width: 100%;
  max-width: 400px;
}

.login-card {
  position: relative;
  background: #ffffff;
  border-radius: 20px;
  padding: 40px 32px;
  box-shadow: 0 20px 60px -15px rgba(15, 23, 42, 0.1), 0 10px 30px -10px rgba(15, 23, 42, 0.06);
  border: 1px solid #e2e8f0;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-wrapper {
  width: 64px;
  height: 64px;
  margin: 0 auto 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-image {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  object-fit: contain;
}

.title {
  font-size: 26px;
  font-weight: 700;
  margin: 0 0 8px;
  color: #0f172a;
}

.desc {
  font-size: 14px;
  color: #64748b;
  margin: 0;
}

.login-form {
  margin-top: 0;
}

.form-input {
  border-radius: 12px;
  border-color: #e2e8f0;
  transition: all 0.25s ease;
}

.form-input:focus {
  border-color: #6366f1;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
}

.tips {
  text-align: right;
  color: #64748b;
  font-size: 13px;
  margin-bottom: 24px;
}

.link {
  color: #6366f1;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.25s ease;
}

.link:hover {
  color: #4f46e5;
}

.submit-btn {
  width: 100%;
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  border: none;
  border-radius: 12px;
  height: 48px;
  font-weight: 600;
  font-size: 16px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.2);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 8px 20px rgba(99, 102, 241, 0.3);
}
</style>
