import { Condition } from '@/model';
import { request } from '../request';

export function fetchQueryChapter(condition: Partial<Condition.Chapter>) {
  return request.post<Dto.Page<Dto.Chapter>>('/chapter/query', condition);
}
export function fetchGetChapter(bookId: number, queue: number) {
  const params = new URLSearchParams();
  params.append('bookId', String(bookId));
  params.append('queue', String(queue));
  return request.get<Dto.Chapter>('/chapter/get?' + params);
}
export function fetchRemoveChapter(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/chapter/remove?${searchParams.toString()}`);
}

export function fetchSaveChapter(chapter: Partial<Dto.Chapter>) {
  return request.post('/chapter/save', chapter);
}

export function fetchUpdateChapter(chapter: Partial<Dto.Chapter>) {
  return request.post('/chapter/update', chapter);
}
