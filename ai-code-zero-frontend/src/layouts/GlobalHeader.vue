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
        class="main-menu"
        @click="handleMenuClick"
      >
        <a-menu-item key="/">首页</a-menu-item>
      </a-menu>

      <!-- 右侧用户区域 -->
      <div class="user-area">
        <a-button type="primary" size="small">登录</a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import type { MenuProps } from 'ant-design-vue';

const router = useRouter();
const currentPath = ref(router.currentRoute.value.path);
const isDarkMode = ref(window.matchMedia('(prefers-color-scheme: dark)').matches);

// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  router.push(e.key);
};

// 监听路由变化
const handleRouteChange = () => {
  currentPath.value = router.currentRoute.value.path;
};

// 监听暗色模式变化
const handleDarkModeChange = (e: MediaQueryListEvent) => {
  isDarkMode.value = e.matches;
};

onMounted(() => {
  // 监听路由变化
  router.afterEach(handleRouteChange);
  // 监听暗色模式变化
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
  mediaQuery.addEventListener('change', handleDarkModeChange);
});

onUnmounted(() => {
  // 清理监听器
  router.afterEach(() => {});
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
  mediaQuery.removeEventListener('change', handleDarkModeChange);
});
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