<template>
  <div class="app-card" :class="{ 'app-card--featured': featured }">
    <div class="app-preview">
      <img v-if="app.cover" :src="app.cover" :alt="app.appName" class="preview-image" />
      <div v-else class="app-placeholder">
        <div class="placeholder-icon">⚡</div>
      </div>
      <div v-if="featured" class="featured-badge">
        <span class="badge-star">★</span>
        <span class="badge-text">精选</span>
      </div>
      <div class="app-overlay">
        <a-space>
          <a-button type="primary" class="overlay-btn primary-btn" @click="handleViewChat">查看对话</a-button>
          <a-button v-if="app.deployKey" type="default" class="overlay-btn secondary-btn" @click="handleViewWork">查看作品</a-button>
        </a-space>
      </div>
    </div>
    <div class="app-info">
      <div class="app-info-left">
        <a-avatar :src="app.user?.userAvatar" :size="36" class="user-avatar">
          {{ app.user?.userName?.charAt(0) || 'U' }}
        </a-avatar>
      </div>
      <div class="app-info-right">
        <h3 class="app-title">{{ app.appName || '未命名应用' }}</h3>
        <p class="app-author">{{ app.user?.userName || (featured ? '官方' : '未知用户') }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
interface Props {
  app: API.AppVO
  featured?: boolean
}

interface Emits {
  (e: 'view-chat', appId: string | number | undefined): void
  (e: 'view-work', app: API.AppVO): void
}

const props = withDefaults(defineProps<Props>(), {
  featured: false,
})

const emit = defineEmits<Emits>()

const handleViewChat = () => {
  emit('view-chat', props.app.id)
}

const handleViewWork = () => {
  emit('view-work', props.app)
}
</script>

<style scoped>
.app-card {
  background: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(15, 23, 42, 0.06), 0 1px 2px rgba(15, 23, 42, 0.04);
  border: 1px solid #e2e8f0;
  transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}

.app-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 30px rgba(15, 23, 42, 0.1), 0 4px 8px rgba(15, 23, 42, 0.06);
  border-color: #cbd5e1;
}

.app-card--featured {
  border-color: rgba(251, 191, 36, 0.3);
  box-shadow: 0 1px 3px rgba(251, 191, 36, 0.08), 0 1px 2px rgba(251, 191, 36, 0.06);
}

.app-card--featured:hover {
  border-color: rgba(251, 191, 36, 0.5);
  box-shadow: 0 12px 30px rgba(251, 191, 36, 0.12), 0 4px 8px rgba(251, 191, 36, 0.08);
}

.app-preview {
  height: 180px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  position: relative;
  border-bottom: 1px solid #f1f5f9;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.app-card:hover .preview-image {
  transform: scale(1.03);
}

.app-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.placeholder-icon {
  font-size: 48px;
  opacity: 0.3;
}

.featured-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  background: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);
  border-radius: 100px;
  font-size: 12px;
  font-weight: 600;
  color: #78350f;
  z-index: 2;
  box-shadow: 0 2px 8px rgba(251, 191, 36, 0.3);
}

.badge-star {
  font-size: 10px;
}

.app-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(15, 23, 42, 0.7);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: all 0.3s ease;
}

.app-card:hover .app-overlay {
  opacity: 1;
}

.overlay-btn {
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.25s ease;
}

.primary-btn {
  background: #6366f1;
  border: none;
  color: #fff;
}

.primary-btn:hover {
  background: #4f46e5;
}

.secondary-btn {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: #fff;
}

.secondary-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.app-info {
  padding: 14px 16px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.app-info-left {
  flex-shrink: 0;
}

.user-avatar {
  border: 2px solid #f1f5f9;
}

.app-info-right {
  flex: 1;
  min-width: 0;
}

.app-title {
  font-size: 15px;
  font-weight: 600;
  margin: 0 0 2px;
  color: #0f172a;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.app-author {
  font-size: 13px;
  color: #94a3b8;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
