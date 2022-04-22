<template>
  <div class="mb-20">
    <n-carousel draggable show-arrow autoplay>
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel1.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel2.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel3.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel4.jpeg"
      />
    </n-carousel>

    <div class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4">
      <div class="border-b py-1 text-base text-amber-500"> 畅销书籍 </div>

      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item of 4" :key="item">
            <router-link to="/detail">
              <BookList />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4">
      <div class="border-b py-1 text-base text-amber-500"> 男生热频 </div>

      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item of 4" :key="item">
            <router-link to="/detail">
              <BookList />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4">
      <div class="border-b py-1 text-base text-amber-500"> 女生热频 </div>
      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
        </n-grid>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
  .carousel-img {
    width: 100%;
    height: 240px;
    object-fit: cover;
  }

  .custom-arrow {
    display: flex;
    position: absolute;
    bottom: 25px;
    right: 10px;
  }
</style>
