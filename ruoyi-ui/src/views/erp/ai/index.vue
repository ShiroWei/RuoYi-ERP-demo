<template>
  <div class="ai-container">
    <!-- 头部 -->
    <div class="ai-header">
      <div class="ai-header-left">
        <div class="ai-avatar"><svg-icon icon-class="build" /></div>
        <div>
          <div class="ai-title">ERP 智能助手</div>
          <div class="ai-sub">演示版 · 规则 Mock 引擎（话术模拟，回复中的经营数字取自真实报表）</div>
        </div>
      </div>
      <el-tag size="mini" type="success" effect="dark">在线</el-tag>
    </div>

    <!-- 消息区 -->
    <div ref="msgList" class="ai-body">
      <div v-for="(m, i) in messages" :key="i" class="msg-row" :class="m.role">
        <div class="msg-avatar">
          <svg-icon v-if="m.role === 'assistant'" icon-class="build" />
          <svg-icon v-else icon-class="user" />
        </div>
        <div class="msg-main">
          <div class="msg-name">{{ m.role === 'assistant' ? 'ERP 智能助手' : '我' }}</div>
          <div class="msg-bubble">
            <div v-if="m.thinking" class="thinking">
              <span class="dot"></span><span class="dot"></span><span class="dot"></span>
            </div>
            <div v-else-if="m.text" class="msg-text" :class="{ typing: m.typing }">{{ m.text }}<span v-if="m.typing" class="caret"></span></div>

            <!-- 统计卡片 -->
            <div v-if="m.cards && m.cards.length" class="msg-cards">
              <div v-for="(c, ci) in m.cards" :key="ci" class="msg-card">
                <div class="msg-card-label">{{ c.label }}</div>
                <div class="msg-card-value">{{ c.value }}</div>
              </div>
            </div>

            <!-- 列表 -->
            <div v-if="m.list && m.list.length" class="msg-list">
              <div v-for="(it, li) in m.list" :key="li" class="msg-list-item">
                <span class="msg-list-title">{{ it.title }}</span>
                <span v-if="it.desc" class="msg-list-desc">{{ it.desc }}</span>
                <el-tag v-if="it.tag" size="mini" :type="it.tagType || 'info'">{{ it.tag }}</el-tag>
              </div>
            </div>

            <!-- 单据预览 -->
            <div v-if="m.preview" class="msg-preview">
              <div class="preview-title">{{ m.preview.title }}</div>
              <div class="preview-fields">
                <div v-for="(f, fi) in m.preview.fields" :key="fi" class="preview-field">
                  <span class="preview-k">{{ f.k }}</span>
                  <span class="preview-v">{{ f.v }}</span>
                </div>
              </div>
              <el-table v-if="m.preview.items && m.preview.items.length" :data="m.preview.items" size="mini" border>
                <el-table-column prop="materialCode" label="物料编码" width="90" />
                <el-table-column prop="materialName" label="物料名称" min-width="110" />
                <el-table-column prop="quantity" label="数量" width="70" align="right" />
                <el-table-column prop="unit" label="单位" width="55" />
                <el-table-column prop="price" label="单价" width="80" align="right" />
                <el-table-column prop="amount" label="金额" width="90" align="right" />
              </el-table>
              <div class="preview-note">
                演示模式：以下为 AI 生成的单据草稿 JSON 预览，未实际提交系统。
                <el-button type="text" size="mini" icon="el-icon-document-copy" @click="copyPreview(m.preview)">复制 JSON</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 快捷提问 -->
    <div v-if="showQuick" class="ai-quick">
      <span class="quick-label">快捷提问：</span>
      <el-tag v-for="(q, qi) in quickList" :key="qi" class="quick-tag" :class="{ active: quickActive === qi }" @click="askQuick(q, qi)">{{ q }}</el-tag>
    </div>

    <!-- 输入区 -->
    <div class="ai-input">
      <el-input
        v-model="input"
        type="textarea"
        :rows="2"
        resize="none"
        placeholder="试试问我：销售怎么样？库存够吗？生成一张采购订单草稿…"
        @keydown.enter.native.prevent="send"
      />
      <el-button type="primary" :loading="thinking" :disabled="!input.trim() && !thinking" icon="el-icon-s-promotion" @click="send">发送</el-button>
    </div>
  </div>
</template>

<script>
import { sendMessage, getWelcomeMessage } from '@/api/erp/ai'

export default {
  name: 'ErpAiAssistant',
  data() {
    return {
      messages: [],
      input: '',
      thinking: false,
      showQuick: true,
      quickActive: -1,
      quickList: ['帮我分析本月销售', '有哪些库存预警？', '汇总一下待审核单据', '分析一下利润情况', '生成一张采购订单草稿', '帮助']
    }
  },
  created() {
    this.messages = [{ role: 'assistant', ...getWelcomeMessage() }]
    this.$nextTick(() => this.scrollToBottom())
  },
  methods: {
    scrollToBottom() {
      this.$nextTick(() => {
        const el = this.$refs.msgList
        if (el) {
          el.scrollTop = el.scrollHeight
        }
      })
    },
    askQuick(q, qi) {
      this.quickActive = qi
      this.input = q
      this.send()
    },
    send() {
      const text = this.input.trim()
      if (!text || this.thinking) return
      this.input = ''
      this.showQuick = false
      this.messages.push({ role: 'user', text })
      const idx = this.messages.push({ role: 'assistant', thinking: true }) - 1
      this.scrollToBottom()
      sendMessage(text).then(res => {
        const delay = res && res.delay ? res.delay : 800
        this.$set(this.messages, idx, {
          role: 'assistant',
          text: '',
          fullText: (res && res.reply) || '',
          cards: (res && res.cards) || [],
          list: (res && res.list) || [],
          preview: (res && res.preview) || null,
          typing: true,
          timer: null
        })
        setTimeout(() => this.typeMessage(idx), 120)
      }).finally(() => {
        this.thinking = false
      })
    },
    typeMessage(idx) {
      const msg = this.messages[idx]
      if (!msg || !msg.typing) return
      const full = msg.fullText || ''
      if (msg.text.length < full.length) {
        msg.text = full.slice(0, msg.text.length + 2)
        this.scrollToBottom()
        msg.timer = setTimeout(() => this.typeMessage(idx), 24)
      } else {
        msg.typing = false
        this.scrollToBottom()
      }
    },
    copyPreview(preview) {
      const json = JSON.stringify(preview, null, 2)
      if (navigator.clipboard && navigator.clipboard.writeText) {
        navigator.clipboard.writeText(json).then(() => this.$modal.msgSuccess('JSON 已复制'))
      } else {
        const ta = document.createElement('textarea')
        ta.value = json
        document.body.appendChild(ta)
        ta.select()
        document.execCommand('copy')
        document.body.removeChild(ta)
        this.$modal.msgSuccess('JSON 已复制')
      }
    }
  },
  beforeDestroy() {
    this.messages.forEach(m => {
      if (m.timer) clearTimeout(m.timer)
    })
  }
}
</script>

<style lang="scss" scoped>
.ai-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 84px);
  background: #f0f2f5;
  border-radius: 6px;
  overflow: hidden;
}

.ai-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #722ed1, #9254de);
  color: #fff;
  padding: 14px 20px;
  flex-shrink: 0;

  .ai-header-left {
    display: flex;
    align-items: center;
  }

  .ai-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.22);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    margin-right: 12px;
  }

  .ai-title {
    font-size: 17px;
    font-weight: 600;
  }

  .ai-sub {
    font-size: 12px;
    opacity: 0.88;
    margin-top: 2px;
  }
}

.ai-body {
  flex: 1;
  overflow-y: auto;
  padding: 20px 24px;
}

.msg-row {
  display: flex;
  margin-bottom: 18px;

  &.user {
    flex-direction: row-reverse;

    .msg-main {
      align-items: flex-end;
    }

    .msg-bubble {
      background: #409eff;
      color: #fff;
    }
  }

  .msg-avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background: #dfe6ef;
    color: #606266;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    margin: 0 10px;
  }

  &.user .msg-avatar {
    background: #409eff;
    color: #fff;
  }

  .msg-main {
    display: flex;
    flex-direction: column;
    max-width: 70%;
  }

  .msg-name {
    font-size: 12px;
    color: #909399;
    margin-bottom: 4px;
    padding: 0 4px;
  }

  .msg-bubble {
    background: #fff;
    border-radius: 10px;
    padding: 12px 14px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
    font-size: 14px;
    line-height: 1.7;
    color: #303133;
  }

  .msg-text {
    white-space: pre-wrap;
    word-break: break-word;
  }

  .caret {
    display: inline-block;
    width: 2px;
    height: 14px;
    background: #722ed1;
    vertical-align: -2px;
    margin-left: 2px;
    animation: blink 0.8s step-end infinite;
  }
}

@keyframes blink {
  50% { opacity: 0; }
}

.thinking {
  display: flex;
  align-items: center;
  padding: 4px 0;

  .dot {
    width: 7px;
    height: 7px;
    border-radius: 50%;
    background: #c0c4cc;
    margin-right: 5px;
    animation: bounce 1.2s infinite;

    &:nth-child(2) { animation-delay: 0.15s; }
    &:nth-child(3) { animation-delay: 0.3s; }
  }
}

@keyframes bounce {
  0%, 80%, 100% { transform: translateY(0); }
  40% { transform: translateY(-6px); }
}

.msg-cards {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;

  .msg-card {
    width: calc(50% - 6px);
    background: #f8f9fb;
    border-radius: 6px;
    padding: 10px 12px;
    margin: 3px;

    .msg-card-label {
      font-size: 12px;
      color: #909399;
    }

    .msg-card-value {
      margin-top: 4px;
      font-size: 18px;
      font-weight: 600;
      color: #303133;
    }
  }
}

.msg-list {
  margin-top: 10px;

  .msg-list-item {
    display: flex;
    align-items: center;
    padding: 6px 2px;
    border-bottom: 1px dashed #ebeef5;

    &:last-child { border-bottom: none; }

    .msg-list-title {
      font-size: 13px;
      color: #303133;
      flex: 1;
    }

    .msg-list-desc {
      font-size: 12px;
      color: #909399;
      margin-right: 8px;
    }
  }
}

.msg-preview {
  margin-top: 10px;
  border: 1px solid #ebeef5;
  border-radius: 6px;
  padding: 12px;
  background: #fafbfc;

  .preview-title {
    font-size: 13px;
    font-weight: 600;
    margin-bottom: 8px;
  }

  .preview-fields {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 8px;

    .preview-field {
      width: 50%;
      display: flex;
      padding: 3px 0;
      font-size: 12px;

      .preview-k {
        color: #909399;
        width: 70px;
        flex-shrink: 0;
      }

      .preview-v {
        color: #303133;
      }
    }
  }

  .preview-note {
    margin-top: 8px;
    font-size: 12px;
    color: #909399;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.ai-quick {
  flex-shrink: 0;
  padding: 8px 24px;
  background: #fff;
  border-top: 1px solid #f0f0f0;

  .quick-label {
    font-size: 13px;
    color: #909399;
    margin-right: 6px;
  }

  .quick-tag {
    margin: 0 6px 6px 0;
    cursor: pointer;
    border-color: #dcdfe6;

    &.active {
      color: #722ed1;
      border-color: #722ed1;
      background: #f5f0ff;
    }
  }
}

.ai-input {
  flex-shrink: 0;
  display: flex;
  align-items: flex-end;
  background: #fff;
  padding: 12px 16px;
  border-top: 1px solid #f0f0f0;

  .el-input {
    flex: 1;
    margin-right: 12px;
  }

  .el-button {
    width: 90px;
  }
}
</style>
