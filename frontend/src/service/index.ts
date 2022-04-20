import { ServiceParams } from 'vue-request/dist/types/core/utils/types';

const baseUrl = import.meta.env.VITE_AXIOS_URL as string;
export function getService(url: string, body?: any): ServiceParams {
  return {
    url: `${baseUrl}/${url}`,
    method: 'POST',
    body: JSON.stringify(body),
    headers: { 'Content-Type': 'application/json' },
    mode: 'cors',
    credentials: 'include',
  };
}
export function getFileService(file: any): ServiceParams {
  return {
    url: `${baseUrl}/file/add`,
    body: file,
    credentials: 'include',
    method: 'post',
    mode: 'cors',
  };
}
