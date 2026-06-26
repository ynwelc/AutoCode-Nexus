<template>
  <a-layout-footer class="footer">
    <div class="footer-content">
      <div class="footer-brand">
        <img :src="logoImg" alt="Logo" class="logo-image" />
        <span class="brand-name">AutoCode Nexus</span>
      </div>
      <p class="copyright">
        <a
          href="https://github.com/ynwelc"
          target="_blank"
          rel="noopener noreferrer"
          class="author-link"
        >
          Github@ynwelc
        </a>
      </p>
      <p class="footer-text">AI 驱动的网站生成平台</p>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
import logoImg from '@/assets/logo.png'
</script>

<style scoped>
.footer {
  background: #f8fafc;
  border-top: 1px solid #e2e8f0;
  padding: 40px 24px 24px;
  margin-top: 60px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.footer-brand {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 12px;
}

.logo-image {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  object-fit: contain;
}

.brand-name {
  font-size: 18px;
  font-weight: 700;
  color: #0f172a;
}

.copyright {
  margin: 0 0 4px;
  color: #64748b;
  font-size: 14px;
}

.author-link {
  color: #475569;
  text-decoration: none;
  transition: color 0.25s ease;
}

.author-link:hover {
  color: #6366f1;
}

.footer-text {
  margin: 0;
  color: #94a3b8;
  font-size: 13px;
}
</style>
