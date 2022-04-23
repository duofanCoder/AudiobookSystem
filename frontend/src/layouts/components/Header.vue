<template>
  <n-space justify="space-between" align="center" class="flex border-b px-6 h-16">
    <div class="flex gap-5 text-xl">
      <router-link to="/" class="text-green-500 font-extrabold text-2xl">听书网</router-link>
    </div>
    <div class="flex items-center gap-5">
      <div>
        <n-input v-model:value="searchKey" @keyup.enter="jump2Click">
          <template #suffix>
            <n-icon> <i-carbon-search /> </n-icon>
          </template>
        </n-input>
      </div>

      <router-link to="/profile">
        <n-icon :size="28">
          <i-ph-user class="my-auto" />
        </n-icon>
      </router-link>
    </div>
  </n-space>
</template>
<script setup lang="ts">
  import { useRouter } from 'vue-router';
  import { ref } from 'vue';
  const searchKey = ref('');

  const router = useRouter();

  const jump2Click = () => {
    router.push({
      name: 'result',
      query: {
        key: searchKey.value,
      },
    });
  };
</script>
<style scoped></style>
