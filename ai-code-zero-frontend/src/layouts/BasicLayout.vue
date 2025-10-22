<template>
  <a-layout class="basic-layout">
    <!-- 全局头部 -->
    <global-header />
    
    <!-- 内容区域 -->
    <a-layout-content class="content-wrapper">
      <div class="content-container">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </a-layout-content>
    
    <!-- 全局底部 -->
    <global-footer />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from './GlobalHeader.vue';
import GlobalFooter from './GlobalFooter.vue';
</script>

<style scoped>
.basic-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  flex: 1;
  padding: 24px;
  background-color: var(--content-bg-color, #ffffff);
}

.content-container {
  max-width: 1440px;
  margin: 0 auto;
  min-height: calc(100vh - 192px); /* 减去header和footer的高度 */
}

/* 过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .content-wrapper {
    padding: 16px;
  }
  
  .content-container {
    min-height: calc(100vh - 176px); /* 适应移动端的不同高度 */
  }
}

/* 暗色模式样式 */
@media (prefers-color-scheme: dark) {
  .content-wrapper {
    --content-bg-color: #141414;
  }
}
</style>