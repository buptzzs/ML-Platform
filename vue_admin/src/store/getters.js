const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  taskname: state => state.user.taskname,
  beginFile: state => state.user.beginFile,
  endFile: state => state.user.endFile,
}
export default getters
