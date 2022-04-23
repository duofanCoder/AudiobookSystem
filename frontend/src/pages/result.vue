<template>
  <div class="w-full flex justify-center">
    <div class="w-55vw">
      <div class="h-12"></div>
      <template v-for="item of 12" :key="item">
        <BookList class="mb-12" @click="jump2Detail" />
      </template>
    </div>
  </div>
</template>
<script setup lang="ts">
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const jump2Detail = () => {
    router.push('/detail');
  };
</script>
<style scoped></style>
