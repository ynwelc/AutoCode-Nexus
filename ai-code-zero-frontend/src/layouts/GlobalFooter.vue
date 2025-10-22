<template>
  <a-layout-footer class="global-footer">
    <div class="footer-content">
      <p class="copyright-text">hhhhh项目——AI零代码生成平台</p>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
// 可以在这里添加footer相关的逻辑
</script>

<style scoped>
.global-footer {
  background-color: var(--footer-bg-color, #f0f2f5);
  padding: 24px 0;
  text-align: center;
  color: var(--text-secondary-color, #666666);
  margin-top: auto;
}

.footer-content {
  max-width: 1440px;
  margin: 0 auto;
  padding: 0 24px;
}

.copyright-text {
  margin: 0;
  font-size: 14px;
}

/* 暗色模式样式 */
@media (prefers-color-scheme: dark) {
  .global-footer {
    --footer-bg-color: #1f1f1f;
    --text-secondary-color: #cccccc;
  }
}
</style>
