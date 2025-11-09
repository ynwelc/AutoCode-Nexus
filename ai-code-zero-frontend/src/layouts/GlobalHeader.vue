<template>
  <a-layout-header class="global-header">
    <div class="header-content">
      <!-- Logo 和 标题 -->
      <div class="logo-container">
        <a href="/" class="logo-wrapper">
          <img src="/favicon.svg" alt="Logo" class="logo-img" />
          <span class="site-title">AI零代码生成平台</span>
        </a>
      </div>

      <!-- 主导航菜单 -->
      <a-menu
        mode="horizontal"
        :theme="isDarkMode ? 'dark' : 'light'"
        :selected-keys="[currentPath]"
        :items="menuItems"
        class="main-menu"
        @click="handleMenuClick"
      >
      </a-menu>

      <!-- 右侧用户区域 -->

      <div class="user-login-status">
        <div v-if="loginUserStore.loginUser.id">
          <a-dropdown>
            <a-space>
              <a-avatar :src="loginUserStore.loginUser.userAvatar || '/favicon.svg'" :size="32" />
              {{ loginUserStore.loginUser.userName ?? '无名' }}
            </a-space>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="doLogout">
                  <logout-outlined />
                  退出登录
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
        <div v-else>
          <a-button type="primary" href="/user/login">登录</a-button>
        </div>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed, h } from 'vue'
import { useRouter } from 'vue-router'
import type { MenuProps } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { LogoutOutlined, HomeOutlined } from '@ant-design/icons-vue'
import { userLogout } from '@/api/userController.ts'
import { message } from 'ant-design-vue'

//获取登录用户状态
const loginUserStore = useLoginUserStore()

const router = useRouter()
const currentPath = ref(router.currentRoute.value.path)
const isDarkMode = ref(window.matchMedia('(prefers-color-scheme: dark)').matches)

// 菜单配置项
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
    key: 'others',
    label: h('a', { href: 'https://www.codefather.cn', target: '_blank' }, '编程导航'),
    title: '编程导航',
  },
]

// 过滤菜单项
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

// 展示在菜单的路由数组
const menuItems = computed<MenuProps['items']>(() => filterMenus(originItems))

// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  router.push(e.key.toString())
}

// 监听路由变化
const handleRouteChange = () => {
  currentPath.value = router.currentRoute.value.path
}

// 监听暗色模式变化
const handleDarkModeChange = (e: MediaQueryListEvent) => {
  isDarkMode.value = e.matches
}

onMounted(() => {
  // 监听路由变化
  router.afterEach(handleRouteChange)
  // 监听暗色模式变化
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
  mediaQuery.addEventListener('change', handleDarkModeChange)
})

// 用户注销
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

onUnmounted(() => {
  // 清理监听器
  router.afterEach(() => {})
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
  mediaQuery.removeEventListener('change', handleDarkModeChange)
})
</script>

<style scoped>
.global-header {
  height: 64px;
  padding: 0;
  background-color: var(--header-bg-color, #ffffff);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.header-content {
  max-width: 1440px;
  height: 100%;
  margin: 0 auto;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo-wrapper {
  display: flex;
  align-items: center;
  color: var(--text-color, #000000);
  text-decoration: none;
}

.logo-img {
  height: 32px;
  width: 32px;
  margin-right: 12px;
}

.site-title {
  font-size: 18px;
  font-weight: 500;
}

.main-menu {
  flex: 1;
  justify-content: center;
}

.user-area {
  display: flex;
  align-items: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    padding: 0 16px;
  }

  .site-title {
    display: none;
  }

  .main-menu {
    display: none;
  }
}
</style>
