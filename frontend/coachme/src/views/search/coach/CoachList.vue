<!-- 코치 리스트 페이지-->

<script setup>
import CoachCardList from '@/components/molecules/CoachCardList.vue'
import chatButton from '@/components/molecules/ChatButton.vue'
import CustomCategory from '@/components/molecules/CustomCategory.vue'
import navbar from '@/components/molecules/LoginNavBar.vue'
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'

import { ref } from 'vue'

const selectButton = ref(0)
// 선택한 카테고리 index

const SideButtonList = [[
  { name: 'House', },
  { name: 'Furniture' },
  { name: 'Lifestyle' },
  { name: 'Design' },
], [
  { name: 'Cocking' },
  { name: 'Knitting' },
  { name: 'Art' },
  { name: 'Beauty' },
], [
  { name: 'Soccer' },
  { name: 'Basketball' },
  { name: 'Tennis' },
  { name: 'Golf' },
], [
  { name: 'Frontend' },
  { name: 'Backend' },
  { name: 'Database' },
  { name: 'Devops' },
], [
  { name: 'Yoga' },
  { name: 'Weight' },
  { name: 'Running' },
  { name: 'Crossfit' },
],]
// 선택한 카테고리에 따라 변경될 사이드 메뉴 리스트

const selectedCategory = ref([])
// 선택된 상단 메뉴 리스트. props로 전달되는 리스트

const clickCategory = (index) => {
  selectButton.value = index
  selectedCategory.value = SideButtonList[selectButton.value]
}
// 카테고리 클릭시 상단 사이드 메뉴 변경

</script>
<template>
  <!-- nav -->
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <!-- 카테고리 -->
      <CustomCategory style="margin-top: 3vh;" @click-category="clickCategory"></CustomCategory>
      <div class="mypage-outside">
        <!-- 사이드메뉴 -->
        <MypageSidebar :button-list="selectedCategory" />
        <div class="mainpage">
          <!-- 코치 리스트와 채팅 버튼 -->
          <CoachCardList />
          <chatButton style="margin-left: 10vw; margin-top: 57vh; width: 50px; height: 50px;">
          </chatButton>
        </div>
      </div>
    </div>
  </div>
  <!-- footer 위치 -->
  <div class="footer">
  </div>
</template>

<style scoped>
.all {
  display: flex;
  justify-content: center;
}

.main-layout {
  width: 1280px;
  min-height: calc(90vh - 70px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.nav-bar {
  width: 100%;
  height: 70px;
  justify-content: center;
  text-align: center;
  border-bottom: #034c8c 1px solid;
  display: flex;
  justify-content: center;
}

.mypage-outside {
  display: flex;
  justify-content: space-between;
}

.mainpage {
  background-color: white;
  width: 80%;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
  display: flex;
  text-align: center;
  flex-direction: row;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>

