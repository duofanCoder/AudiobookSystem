import { request } from '../request';

export function fetchQueryUser(condition: Partial<Condition.Common>) {
  return request.post<Dto.Page<Dto.User>>('/user/query', condition);
}

export function fetchRemoveUser(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/user/remove?${searchParams.toString()}`);
}
export function fetchRegisterUser(username: string, password: string) {
  return request.post<Dto.Token>('/user/signup', { username, password });
}
export function fetchSaveUser(user: Partial<Dto.User>) {
  return request.post('/user/save', user);
}

export function fetchUpdateUser(user: Partial<Dto.User>) {
  console.log(user);
  return request.post('/user/update', user);
}

export function fetchUpdateProfile(user: Dto.User) {
  return request.post('/user/profile', user);
}
export function fetchResetUser(id: number) {
  return request.post('/user/reset', {
    id,
    password: '123456',
  });
}

export function fetchPasswordUser(password: string) {
  return request.post('/user/password', { password });
}
