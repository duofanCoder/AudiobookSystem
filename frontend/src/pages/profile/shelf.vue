<template>
  <div class="flex flex-col flex-1 mx-auto max-w-235">
    <div class="text-center">
      <p class="font-medium text-3xl mt-12"> 书架 </p>
      <p class="mt-3 text-lg">查看您收藏过的书籍。</p>
    </div>

    <div class="border py-5 mt-6 rounded-xl bg-white px-6">
      <p class="text-2xl">书籍</p>
      <p class="mt-2 tracking-widest mb-4">收藏过的书籍都会出现在这里。</p>
      <!-- w-50 -->
      <template v-for="item of 12" :key="item">
        <div class="flex mb-6">
          <BookList class="pr-12 hover:cursor-pointer" @click="jump2Detail" />
          <div class="h-full flex">
            <n-button type="error" class="mb-auto">移除</n-button>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  const active = ref(false);

  const router = useRouter();

  const jump2Detail = () => {
    router.push('/detail');
  };
</script>

<style scoped></style>
