import { createGlobalState, useStorage } from '@vueuse/core';

export const useState = createGlobalState(() =>
  useStorage('user', {
    ACCESS_TOKEN: '',
    CURRENT_USER: '',
  })
);
