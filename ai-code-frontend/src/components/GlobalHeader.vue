<template>
  <a-layout-header class="header">
    <a-row :wrap="false">
      <a-col flex="200px">
        <RouterLink to="/">
          <div class="header-left">
            <div class="logo-wrapper">
              <img :src="logoImg" alt="Logo" class="logo-image" />
            </div>
            <h1 class="site-title">AutoCode Nexus</h1>
          </div>
        </RouterLink>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="horizontal"
          :items="menuItems"
          @click="handleMenuClick"
          class="main-menu"
        />
      </a-col>
      <a-col>
        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.id">
            <a-dropdown>
              <a-space class="user-info">
                <a-avatar :src="loginUserStore.loginUser.userAvatar" class="user-avatar">
                  {{ loginUserStore.loginUser.userName?.charAt(0) || 'U' }}
                </a-avatar>
                <span class="user-name">{{ loginUserStore.loginUser.userName ?? '无名' }}</span>
                <span class="caret">▼</span>
              </a-space>
              <template #overlay>
                <a-menu class="dropdown-menu">
                  <a-menu-item @click="doLogout">
                    <LogoutOutlined />
                    <span>退出登录</span>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div v-else>
            <a-button type="primary" href="/user/login" class="login-btn">
              登录
            </a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </a-layout-header>
</template>

<script setup lang="ts">
import { computed, h, ref } from 'vue'
import { useRouter } from 'vue-router'
import { type MenuProps, message } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { userLogout } from '@/api/userController.ts'
import { LogoutOutlined, HomeOutlined } from '@ant-design/icons-vue'
import logoImg from '@/assets/logo.png'

const loginUserStore = useLoginUserStore()
const router = useRouter()
const selectedKeys = ref<string[]>(['/'])

router.afterEach((to) => {
  selectedKeys.value = [to.path]
})

const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/admin/appManage',
    label: '应用管理',
    title: '应用管理',
  },
  {
    key: 'others',
    label: h('a', { href: 'https://github.com/ynwelc/AutoCode-Nexus', target: '_blank' }, '源代码'),
    title: '源代码',
  },
]

const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    const menuKey = menu?.key as string
    if (menuKey?.startsWith('/admin')) {
      const loginUser = loginUserStore.loginUser
      if (!loginUser || loginUser.userRole !== 'admin') {
        return false
      }
    }
    return true
  })
}

const menuItems = computed<MenuProps['items']>(() => filterMenus(originItems))

const handleMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  if (key.startsWith('/')) {
    router.push(key)
  }
}

const doLogout = async () => {
  const res = await userLogout()
  if (res.data.code === 0) {
    loginUserStore.setLoginUser({
      userName: '未登录',
    })
    message.success('退出登录成功')
    await router.push('/user/login')
  } else {
    message.error('退出登录失败，' + res.data.message)
  }
}
</script>

<style scoped>
.header {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  padding: 0 24px;
  border-bottom: 1px solid #f1f5f9;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-wrapper {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-image {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  object-fit: contain;
}

.site-title {
  margin: 0;
  font-size: 18px;
  font-weight: 700;
  color: #0f172a;
  letter-spacing: 0.5px;
  white-space: nowrap;
}

.main-menu {
  border-bottom: none;
  background: transparent;
}

.main-menu .ant-menu-item {
  color: #64748b;
  font-weight: 500;
  padding: 0 16px;
  margin: 0 2px;
  border-radius: 8px;
  transition: all 0.25s ease;
}

.main-menu .ant-menu-item:hover {
  color: #0f172a;
  background: #f8fafc;
}

.main-menu .ant-menu-item-selected {
  color: #6366f1;
  background: rgba(99, 102, 241, 0.08);
}

.main-menu .ant-menu-item-selected::after {
  border-bottom: none;
}

.user-login-status {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  border-radius: 100px;
  transition: background 0.25s ease;
  cursor: pointer;
}

.user-info:hover {
  background: #f8fafc;
}

.user-avatar {
  width: 32px;
  height: 32px;
  border: 2px solid rgba(99, 102, 241, 0.2);
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: #0f172a;
}

.caret {
  font-size: 10px;
  color: #94a3b8;
  transition: transform 0.25s ease;
}

.user-info:hover .caret {
  transform: rotate(180deg);
}

.login-btn {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  border: none;
  border-radius: 10px;
  font-weight: 500;
  padding: 6px 20px;
  transition: all 0.25s ease;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.2);
}

.login-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.3);
}

.dropdown-menu {
  background: #ffffff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(15, 23, 42, 0.1);
}

.dropdown-menu .ant-menu-item {
  color: #475569;
  border-radius: 8px;
  margin: 4px;
}

.dropdown-menu .ant-menu-item:hover {
  background: #f8fafc;
  color: #0f172a;
}
</style>
