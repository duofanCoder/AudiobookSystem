<template>
  <n-menu
    @update:value="handleUpdateValue"
    :options="menuOptions"
    collapse-mode="width"
    width="44"
    :inverted="inverted"
    :collapsed-width="64"
    :collapsed="collapsed"
    :collapsed-icon-size="22"
  />
</template>

<script setup lang="ts">
  import { rendreIcon } from '@/utils';
  import { useRouter } from 'vue-router';

  const props = defineProps({
    collapsed: Boolean,
    inverted: Boolean,
  });
  const menuOptions = [
    {
      label: '控制台',
      key: 'dashboard',
      icon: rendreIcon('ant-design:dashboard-outlined'),
      children: [
        {
          label: '分析页',
          key: 'dashboard_analysis',
        },
      ],
    },
    {
      label: '文章管理',
      key: 'article',
      icon: rendreIcon('mdi:bookshelf'),
      children: [
        {
          label: '文章列表',
          key: 'article_list',
        },
        {
          label: '标签列表',
          key: 'article_tags',
        },
        {
          label: '分类管理',
          key: 'article_type',
        },
      ],
    },
  ];
  const router = useRouter();

  const handleUpdateValue = (key: string, _item: object) => {
    router.push({
      name: key,
    });
  };
</script>
