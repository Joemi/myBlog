<template>
    <div class="root">
        <!-- <div class="sidebar">
            <router-link :to="{path: '/index'}">首页</router-link>
            <router-link :to="{path: '/management'}">管理页面</router-link>
        </div> -->
        <router-view></router-view>
    </div>
</template>

<script>
    import VueRouter from 'vue-router';
    import routes from './routes/route';
    const router = new VueRouter({routes});
    export default {
        name: 'Index',
        router,
        data () {
            return {

            }
        },
        methods: {

        },
        mounted () {

        },
        watch: {

        }
    }
</script>

<style>
 .root {
     width: 100%;
     height: 100%;
     font-family: Microsoft YaHei;
 }

 .sidebar {
     left: 0px;
     top: 0px;
 }

 .body {
    padding: 0;
    margin: 0;
 }

</style>