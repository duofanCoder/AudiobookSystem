<template>
  <div class="flex flex-col md:( flex-row) min-h-140">
    <div
      class="flex flex-col text-lg space-y-5 mt-16"
      :class="[route.name == 'profile-modify' ? 'invisible' : '']"
    >
      <router-link
        to="/admin"
        class="flex pl-8 py-3 rounded-r-full w-45 hover:( bg-gray-200)"
        :class="[route.name == 'admin' ? 'menuActive' : '']"
      >
        <i-carbon-user-avatar-filled-alt class="h-7 w-7 self-center mr-3" /> <p> 用户管理</p>
      </router-link>
      <router-link
        to="/admin/book"
        class="flex pl-8 py-3 rounded-r-full w-45 hover:( bg-gray-200)"
        :class="[route.name == 'admin-book' ? 'menuActive' : '']"
        ><i-uil-feedback class="h-7 w-7 self-center mr-3" />
        <p>书籍管理</p>
      </router-link>
      <div
        class="flex cursor-pointer pl-8 py-3 rounded-r-full w-full hover:( bg-gray-200)"
        @click="logout"
        ><i-mdi-logout class="h-7 w-7 self-center mr-3" />
        <p>注销</p>
      </div>
    </div>

    <router-view></router-view>
  </div>
</template>
<script setup lang="ts">
  import { useUserStore } from '@/store';
  import { useRoute } from 'vue-router';

  const { logout } = useUserStore();

  const route = useRoute();
</script>
<style scoped>
  .menuActive {
    color: rgba(5, 150, 105);
    --tw-bg-opacity: 1;
    background-color: rgba(209, 250, 229, var(--tw-bg-opacity));
  }
</style>
