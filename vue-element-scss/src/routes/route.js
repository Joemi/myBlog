import Index from '../components/index.vue';
import Management from '../components/management.vue';
import Login from '../views/login.vue';
import Register from '../views/register.vue';
import Home from '../views/home.vue';
import HomePage from '../views/homePage.vue';
import AboutMe from '../views/aboutMe.vue';
import EditAboutMe from '../views/editAboutMe.vue';
import Leaving from '../views/leaving.vue';
import Blog from '../views/blogManage.vue';
import EditBlog from '../components/editBlog.vue';
import Letter from '../views/letterManage.vue';
import LetterDetail from '../views/LetterDetail.vue';
import CheckBlog from '../views/viewBlog.vue';
import ManageHome from '../components/manageHome.vue';
import UserManage from '../components/userManage.vue';
import ExamineManage from '../components/ExamineManage.vue';
import ParamManage from '../components/paramListManage.vue';
import Album from '../views/photoAlbum.vue';


export default [
    { path: '/', redirect: '/index'},
    {path: '/index',name:'index', component: Index},
    {path: '/login',name:'login', component: Login},
    {path: '/register',name:'register', component: Register},
    { path: '/home', redirect: '/home/homePage'},
    {
        path: '/home', 
        component: Home,
        children:[
            // {path: 'management',name:'management', component: Management,meta: {requireAuth: true}},
            {path: 'homePage',name:'homePage', component: HomePage,meta: {requireAuth: true}},
            {path: 'aboutMe',name:'aboutMe', component: AboutMe,meta: {requireAuth: true}},
            {path: 'editAboutMe',name:'editAboutMe', component: EditAboutMe,meta: {requireAuth: true}},
            {path: 'leaving',name:'leaving', component: Leaving,meta: {requireAuth: true}},
            {path: 'blog',name:'blog',component: Blog,meta: {requireAuth: true}},
            {path: 'editBlog',name:'editBlog',component: EditBlog,meta: {requireAuth: true}},
            {path: 'checkBlog',name:'checkBlog',component: CheckBlog, meta: {requireAuth: true}},
            {path: 'letter',name:'letter',component: Letter, meta: {requireAuth: true}},
            {path: 'letterDetail',name: 'letterDetail',component: LetterDetail, meta: {requireAuth: true}},
            {path: 'album', name: 'album',component: Album, meta: {requireAuth: true}}
        ],
    },
    {path: '/manageHome',name:'manageHome', component: ManageHome},
    {
        path: '/manageHome', 
        component: ManageHome,
        children:[
            {path: 'userManage',name:'userManage', component: UserManage, meta: {requireAuth: true}},
            {path: 'examineManage',name:'examineManage', component: ExamineManage, meta: {requireAuth: true}},
            {path: 'paramManage',name:'paramManage', component: ParamManage, meta: {requireAuth: true}}
            ]
    }
]
