export * from './crypto';
export * from './typeof';
export * from './auth';
export * from './system';

// function hello(state: API.CampaignState) {
//   if (state == API.CampaignState.AUDIT_FIRST || API.CampaignState.AUDIT_SECOND == state) {
//     return '审核中';
//   } else if (state == API.CampaignState.DRAFT_FIRST || state == API.CampaignState.DRAFT_SECOND) {
//     return '草稿';
//   } else if (state == API.CampaignState.FAIL_FIRST) {
//     return '孵化失败';
//   } else if (state == API.CampaignState.FAIL_SECOND) {
//     return '众筹失败';
//   } else if (state == API.CampaignState.UNDERWAY_FIRST) {
//     return '孵化中';
//   } else if (state == API.CampaignState.UNDERWAY_SECOND) {
//     return '众筹中';
//   } else if (state == API.CampaignState.REJECT_FIRST || state == API.CampaignState.REJECT_SECOND) {
//     return '未过审';
//   } else if (state == API.CampaignState.SUCCESS_FIRST) {
//     return '孵化成功';
//   } else if (state == API.CampaignState.SUCCESS_SECOND) {
//     return '众筹成功';
//   } else if (state == API.CampaignState.FINISH_FIRST) {
//     return '孵化已过时';
//   } else if (state == API.CampaignState.FINISH_SECOND) {
//     return '众筹已过时';
//   } else {
//     return '未知状态';
//   }
// }
