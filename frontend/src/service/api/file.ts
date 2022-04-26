import { request } from '../request';

export function fetchFileUpload(data: FormData) {
  // var configs = {
  //   headers: { 'Content-Type': 'multipart/form-data' },
  // };
  return request.post<Array<API.File>>('/file/upload', data);
}
