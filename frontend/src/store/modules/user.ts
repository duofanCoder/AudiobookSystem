import { defineStore } from 'pinia';
import { useState } from '../Storage';
import { useRequest } from 'vue-request';
import { getService } from '@/service';
import { useThrottledRefHistory } from '@vueuse/core';

const state = useState();
export interface IUserState {
  token: string;
  username: string;
  welcome: string;
  avatar: string;
  permissions: any[];
  info: any;
}
const ACCESS_TOKEN = 'ACCESS-TOKEN'; // 用户token
const CURRENT_USER = 'CURRENT-USER'; // 当前用户信息

export const useUserStore = defineStore({
  id: 'app-user',
  state: (): IUserState => ({
    token: state.value.ACCESS_TOKEN,
    username: '',
    welcome: '',
    avatar: '',
    permissions: [],
    info: state.value.CURRENT_USER,
  }),
  getters: {
    getToken(): string {
      return this.token;
    },
    getAvatar(): string {
      return this.avatar;
    },
    getNickname(): string {
      return this.username;
    },
    getPermissions(): [any][] {
      return this.permissions;
    },
    getUserInfo(): object {
      return this.info;
    },
  },
  actions: {
    setToken(token: string) {
      this.token = token;
    },
    setAvatar(avatar: string) {
      this.avatar = avatar;
    },
    setPermissions(permissions: []) {
      this.permissions = permissions;
    },
    setUserInfo(info: any) {
      this.info = info;
    },
    login(token: string, user: API.User) {
      user.password = '';
      state.value.ACCESS_TOKEN = token;
      state.value.CURRENT_USER = user.username;
      this.info = user;
    },
    logout() {
      this.setPermissions([]);
      this.setUserInfo('');
      state.value.ACCESS_TOKEN = '';
      state.value.CURRENT_USER = '';
    },
  },
});
