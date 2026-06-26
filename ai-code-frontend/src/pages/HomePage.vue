<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { useLoginUserStore } from '@/stores/loginUser'
import { addApp, listMyAppVoByPage, listGoodAppVoByPage } from '@/api/appController'
import { getDeployUrl } from '@/config/env'
import AppCard from '@/components/AppCard.vue'

const router = useRouter()
const loginUserStore = useLoginUserStore()

const userPrompt = ref('')
const creating = ref(false)

const myApps = ref<API.AppVO[]>([])
const myAppsPage = reactive({
  current: 1,
  pageSize: 6,
  total: 0,
})

const featuredApps = ref<API.AppVO[]>([])
const featuredAppsPage = reactive({
  current: 1,
  pageSize: 6,
  total: 0,
})

const setPrompt = (prompt: string) => {
  userPrompt.value = prompt
}

const createApp = async () => {
  if (!userPrompt.value.trim()) {
    message.warning('请输入应用描述')
    return
  }

  if (!loginUserStore.loginUser.id) {
    message.warning('请先登录')
    await router.push('/user/login')
    return
  }

  creating.value = true
  try {
    const res = await addApp({
      initPrompt: userPrompt.value.trim(),
    })

    if (res.data.code === 0 && res.data.data) {
      message.success('应用创建成功')
      const appId = String(res.data.data)
      await router.push(`/app/chat/${appId}`)
    } else {
      message.error('创建失败：' + res.data.message)
    }
  } catch (error) {
    console.error('创建应用失败：', error)
    message.error('创建失败，请重试')
  } finally {
    creating.value = false
  }
}

const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault()
    createApp()
  }
}

const loadMyApps = async () => {
  if (!loginUserStore.loginUser.id) {
    return
  }

  try {
    const res = await listMyAppVoByPage({
      pageNum: myAppsPage.current,
      pageSize: myAppsPage.pageSize,
      sortField: 'createTime',
      sortOrder: 'desc',
    })

    if (res.data.code === 0 && res.data.data) {
      myApps.value = res.data.data.records || []
      myAppsPage.total = res.data.data.totalRow || 0
    }
  } catch (error) {
    console.error('加载我的应用失败：', error)
  }
}

const loadFeaturedApps = async () => {
  try {
    const res = await listGoodAppVoByPage({
      pageNum: featuredAppsPage.current,
      pageSize: featuredAppsPage.pageSize,
      sortField: 'createTime',
      sortOrder: 'desc',
    })

    if (res.data.code === 0 && res.data.data) {
      featuredApps.value = res.data.data.records || []
      featuredAppsPage.total = res.data.data.totalRow || 0
    }
  } catch (error) {
    console.error('加载精选应用失败：', error)
  }
}

const viewChat = (appId: string | number | undefined) => {
  if (appId) {
    router.push(`/app/chat/${appId}?view=1`)
  }
}

const viewWork = (app: API.AppVO) => {
  if (app.deployKey) {
    const url = getDeployUrl(app.deployKey)
    window.open(url, '_blank')
  }
}

onMounted(() => {
  loadMyApps()
  loadFeaturedApps()
})
</script>

<template>
  <div id="homePage">
    <div class="container">
      <div class="hero-section">
        <div class="hero-content">
        <div class="hero-tag">
            <span class="tag-dot"></span>
            AI 驱动 · 无需代码
          </div>
          <h1 class="hero-title">
            用一句话，
            <span class="gradient-text">生成你的网站</span>
          </h1>
          <p class="hero-description">
            描述你想要的网站，AI 帮你从零构建。
            <br />
            从设计、写代码、一键部署，全部搞定。
          </p>
        </div>

        <div class="input-section">
          <div class="input-card">
            <div class="input-wrapper">
              <a-textarea
                v-model:value="userPrompt"
                placeholder="描述你想要的网站，例如：创建一个现代简约风格的个人博客..."
                :rows="3"
                :maxlength="1000"
                class="prompt-input"
                @keydown="handleKeydown"
              />
            </div>
            <div class="input-footer">
              <div class="input-hint">
                <span class="hint-key">Enter</span>
                <span class="hint-text">回车生成</span>
              </div>
              <a-button type="primary" @click="createApp" :loading="creating" class="submit-btn">
                <template #icon>
                  <span class="send-icon">↵</span>
                </template>
                生成
              </a-button>
            </div>
          </div>
        </div>

        <div class="quick-actions">
          <span class="quick-label">试试这些：</span>
          <a-button
            type="default"
            @click="
              setPrompt(
                '创建一个现代化的个人博客网站，包含文章列表、详情页、分类标签、搜索功能、评论系统和个人简介页面。采用简洁的设计风格，支持响应式布局，文章支持Markdown格式，首页展示最新文章和热门推荐。',
              )
            "
            class="quick-btn"
          >
            个人博客
          </a-button>
          <a-button
            type="default"
            @click="
              setPrompt(
                '设计一个专业的企业官网，包含公司介绍、产品服务展示、新闻资讯、联系我们等页面。采用商务风格的设计，包含轮播图、产品展示卡片、团队介绍、客户案例展示。',
              )
            "
            class="quick-btn"
          >
            企业官网
          </a-button>
          <a-button
            type="default"
            @click="
              setPrompt(
                '构建一个功能完整的在线商城，包含商品展示、购物车、用户注册登录、订单管理、支付结算等功能。设计现代化的商品卡片布局，支持商品搜索筛选。',
              )
            "
            class="quick-btn"
          >
            在线商城
          </a-button>
          <a-button
            type="default"
            @click="
              setPrompt(
                '制作一个精美的作品展示网站，适合设计师、摄影师、艺术家等创作者。包含作品画廊、项目详情页、个人简历、联系方式等模块。',
              )
            "
            class="quick-btn"
          >
            作品展示
          </a-button>
        </div>
      </div>

      <div class="section">
        <div class="section-header">
          <h2 class="section-title">精选案例</h2>
          <p class="section-subtitle">来自社区的优秀作品</p>
        </div>
        <div class="featured-grid">
          <AppCard
            v-for="app in featuredApps"
            :key="app.id"
            :app="app"
            :featured="true"
            @view-chat="viewChat"
            @view-work="viewWork"
          />
        </div>
        <div v-if="featuredAppsPage.total > 0" class="pagination-wrapper">
          <a-pagination
            v-model:current="featuredAppsPage.current"
            v-model:page-size="featuredAppsPage.pageSize"
            :total="featuredAppsPage.total"
            :show-size-changer="false"
            :show-total="(total: number) => `共 ${total} 个案例`"
            @change="loadFeaturedApps"
            class="pagination"
          />
        </div>
      </div>

      <div v-if="loginUserStore.loginUser.id" class="section">
        <div class="section-header">
          <h2 class="section-title">我的作品</h2>
          <p class="section-subtitle">你创建的应用</p>
        </div>
        <div class="app-grid">
          <AppCard
            v-for="app in myApps"
            :key="app.id"
            :app="app"
            @view-chat="viewChat"
            @view-work="viewWork"
          />
        </div>
        <div v-if="myAppsPage.total > 0" class="pagination-wrapper">
          <a-pagination
            v-model:current="myAppsPage.current"
            v-model:page-size="myAppsPage.pageSize"
            :total="myAppsPage.total"
            :show-size-changer="false"
            :show-total="(total: number) => `共 ${total} 个应用`"
            @change="loadMyApps"
            class="pagination"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#homePage {
  width: 100%;
  min-height: 100vh;
  background:
    linear-gradient(180deg, #ffffff 0%, #f8fafc 40%, #f1f5f9 100%);
  position: relative;
  overflow: hidden;
}

#homePage::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background:
    radial-gradient(ellipse 800px 500px at 10% -10%, rgba(99, 102, 241, 0.08) 0%, transparent 70%),
    radial-gradient(ellipse 600px 400px at 90% 10%, rgba(167, 139, 250, 0.06) 0%, transparent 70%),
    radial-gradient(ellipse 500px 300px at 50% 100%, rgba(59, 130, 246, 0.05) 0%, transparent 70%);
  pointer-events: none;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  position: relative;
  z-index: 2;
  width: 100%;
}

.hero-section {
  text-align: center;
  padding: 80px 0 60px;
  position: relative;
}

.hero-content {
  position: relative;
  z-index: 2;
}

.hero-tag {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 6px 16px;
  background: rgba(99, 102, 241, 0.08);
  border: 1px solid rgba(99, 102, 241, 0.12);
  border-radius: 100px;
  font-size: 13px;
  color: #6366f1;
  font-weight: 500;
  margin-bottom: 28px;
}

.tag-dot {
  width: 6px;
  height: 6px;
  background: #22c55e;
  border-radius: 50%;
}

.hero-title {
  font-size: 56px;
  font-weight: 700;
  margin: 0 0 20px;
  line-height: 1.15;
  color: #0f172a;
  letter-spacing: -0.02em;
}

.gradient-text {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 50%, #ec4899 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-description {
  font-size: 18px;
  margin: 0 auto;
  color: #64748b;
  max-width: 520px;
  line-height: 1.7;
}

.input-section {
  position: relative;
  margin: 48px auto 32px;
  max-width: 640px;
}

.input-card {
  background: #ffffff;
  border-radius: 20px;
  padding: 8px 8px 12px;
  box-shadow:
    0 20px 60px -15px rgba(99, 102, 241, 0.15),
    0 10px 30px -10px rgba(15, 23, 42, 0.1);
  border: 1px solid #e2e8f0;
  transition: all 0.3s ease;
}

.input-card:focus-within {
  box-shadow:
    0 25px 70px -15px rgba(99, 102, 241, 0.2),
    0 15px 40px -10px rgba(15, 23, 42, 0.12);
  border-color: rgba(99, 102, 241, 0.3);
}

.input-wrapper {
  position: relative;
}

.prompt-input {
  border: none;
  font-size: 15px;
  padding: 20px 20px 12px;
  background: transparent;
  color: #0f172a;
  resize: none;
  box-shadow: none;
}

.prompt-input :deep(.ant-input) {
  border: none;
  background: transparent;
  box-shadow: none;
  font-size: 15px;
  color: #0f172a;
}

.prompt-input :deep(.ant-input:focus) {
  border: none;
  box-shadow: none;
}

.prompt-input :deep(.ant-input::placeholder) {
  color: #94a3b8;
}

.input-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
}

.input-hint {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: #94a3b8;
}

.hint-key {
  padding: 2px 6px;
  background: #f1f5f9;
  border: 1px solid #e2e8f0;
  border-radius: 4px;
  font-size: 11px;
  font-weight: 500;
  color: #64748b;
}

.submit-btn {
  background: linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%);
  border: none;
  border-radius: 10px;
  height: 40px;
  font-weight: 600;
  padding: 0 24px;
  font-size: 14px;
  transition: all 0.25s ease;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
}

.submit-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.4);
}

.send-icon {
  font-size: 14px;
  font-weight: 700;
}

.quick-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
}

.quick-label {
  font-size: 13px;
  color: #94a3b8;
  margin-right: 4px;
}

.quick-btn {
  padding: 8px 16px;
  height: auto;
  background: #ffffff;
  border: 1px solid #e2e8f0;
  color: #475569;
  border-radius: 10px;
  transition: all 0.25s ease;
  font-size: 13px;
  font-weight: 500;
}

.quick-btn:hover {
  background: #f8fafc;
  border-color: #cbd5e1;
  color: #0f172a;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.08);
}

.section {
  margin-bottom: 80px;
}

.section-header {
  text-align: center;
  margin-bottom: 40px;
}

.section-title {
  font-size: 32px;
  font-weight: 700;
  color: #0f172a;
  margin: 0 0 8px;
  letter-spacing: -0.02em;
}

.section-subtitle {
  font-size: 15px;
  color: #64748b;
  margin: 0;
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.featured-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

.pagination {
  .ant-pagination-item {
    background: #ffffff;
    border-color: #e2e8f0;
    a {
      color: #475569;
    }
  }
  .ant-pagination-item:hover {
    border-color: #6366f1;
    a {
      color: #6366f1;
    }
  }
  .ant-pagination-item-active {
    background: #6366f1;
    border-color: #6366f1;
    a {
      color: #fff;
    }
  }
  .ant-pagination-prev,
  .ant-pagination-next {
    color: #475569;
    border-color: #e2e8f0;
  }
  .ant-pagination-prev:hover,
  .ant-pagination-next:hover {
    color: #6366f1;
    border-color: #6366f1;
  }
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 36px;
  }

  .hero-description {
    font-size: 16px;
  }

  .app-grid,
  .featured-grid {
    grid-template-columns: 1fr;
  }

  .section-title {
    font-size: 24px;
  }
}
</style>
