<template>
  <div id="userRegisterPage">
    <h2 class="title">AI 应用生成 - 用户注册</h2>
    <div class="desc">不写一行代码，生成完整应用</div>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit">
      <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入账号' }]">
        <a-input v-model:value="formState.userAccount" placeholder="请输入账号" />
      </a-form-item>

      <a-form-item
        name="userPassword"
        :rules="[
          { required: true, message: '请输入密码' },
          { min: 8, message: '密码长度不能小于8位' },
        ]"
      >
        <a-input-password v-model:value="formState.userPassword" placeholder="请输入密码" />
      </a-form-item>

      <a-form-item
        name="checkPassword"
        :rules="[
          { required: true, message: '请确认密码' },
          { validator: checkPasswordValidator }
        ]"
      >
        <a-input-password v-model:value="formState.checkPassword" placeholder="请确认密码" />
      </a-form-item>

      <div class="tips">
        已有账号
        <RouterLink to="/user/login">去登录</RouterLink>
      </div>

      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">注册</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import { userRegister } from '@/api/userController'
import { reactive } from 'vue'
import { message } from 'ant-design-vue'
import { useRouter, RouterLink } from 'vue-router'

/**
 * 密码确认验证器
 */
const checkPasswordValidator = (_: any, value: string) => {
  if (!value || formState.userPassword === value) {
    return Promise.resolve();
  } else {
    return Promise.reject(new Error('两次输入的密码不一致'));
  }
}

const formState = reactive<API.UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

const router = useRouter()

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  const res = await userRegister(values)
  if (res.data.code === 0 && res.data.data) {
    message.success('注册成功')
    router.push({
      path: '/user/login',
      replace: true,
    })
  } else {
    message.error('注册失败' + res.data.message)
  }
}
</script>
<style scoped>
/* 页面容器 */
#userRegisterPage {
  max-width: 420px;
  margin: 60px auto;
  padding: 32px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  transition: box-shadow 0.3s ease;
}

#userRegisterPage:hover {
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.12);
}

/* 标题样式 */
.title {
  text-align: center;
  font-size: 24px;
  font-weight: 600;
  color: #1890ff;
  margin-bottom: 8px;
  line-height: 1.4;
}

/* 描述样式 */
.desc {
  text-align: center;
  color: #666666;
  margin-bottom: 32px;
  font-size: 14px;
  line-height: 1.6;
}

/* 表单样式 */
:deep(.ant-form) {
  margin-bottom: 24px;
}

/* 输入框样式优化 */
:deep(.ant-input-affix-wrapper),
:deep(.ant-input) {
  font-size: 14px;
  height: 44px;
  border-radius: 8px;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

/* 专门针对密码输入框的样式调整 */
:deep(.ant-input-affix-wrapper) {
  padding: 0 12px;
}

:deep(.ant-input-affix-wrapper .ant-input) {
  height: 100%;
  padding: 4px 0;
  border: none;
  box-shadow: none;
}

:deep(.ant-input-affix-wrapper:hover) {
  border-color: #40a9ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

:deep(.ant-input-affix-wrapper:focus-within) {
  border-color: #1890ff;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.3);
}

:deep(.ant-input:hover) {
  border-color: #40a9ff;
  box-shadow: none;
}

:deep(.ant-input:focus) {
  border-color: #1890ff;
  box-shadow: none;
}

/* 密码可见性切换按钮样式调整 */
:deep(.ant-input-password-icon) {
  margin: 0;
  font-size: 16px;
  color: #999;
}

:deep(.ant-input-password-icon:hover) {
  color: #40a9ff;
}

/* 按钮样式优化 */
:deep(.ant-btn-primary) {
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  background-color: #1890ff;
  border-color: #1890ff;
  transition: all 0.3s ease;
}

:deep(.ant-btn-primary:hover) {
  background-color: #40a9ff;
  border-color: #40a9ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

:deep(.ant-btn-primary:active) {
  transform: translateY(0);
}

/* 提示文字样式 */
.tips {
  text-align: right;
  margin-top: 16px;
  font-size: 14px;
}

.tips a {
  color: #1890ff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.tips a:hover {
  color: #40a9ff;
  text-decoration: underline;
}

/* 错误提示样式 */
:deep(.ant-form-item-explain-error) {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 4px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  #userRegisterPage {
    margin: 40px 20px;
    padding: 24px;
  }
  
  .title {
    font-size: 20px;
  }
}
</style>
