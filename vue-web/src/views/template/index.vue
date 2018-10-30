<style scoped>
    .layout{
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;
    }
    .layout-logo{
        float: left;
    }
    .layout-search{
        height: 30px;
        border-radius: 3px;
        float: left;
        position: relative;
        left: 80px;
        
    }
    .layout-nav{
        height: inherit;
        float: right;
    }
    .layout-footer-center{
        text-align: center;
    }
    @keyframes ani-demo-spin {
        from { transform: rotate(0deg);}
        50%  { transform: rotate(180deg);}
        to   { transform: rotate(360deg);}
    }
</style>
<template>
    <div class="layout">
    	<Layout>
            <Header style="position: fixed;width: 100%;background:#fff;padding:0 0;z-index: 1000; ">
                <Menu mode="horizontal" theme="light"  :style="{height:'65px',width:'100%'}" @on-select="m=>{menuSelect(m)}">
                    <div style="width: 95%;margin: 0 auto">
                        <div class="layout-logo">
                            <a @click="backHome()">
                                <img src="../../images/logo.jpg" style="width: 50px;height: 50px;" align="absmiddle"/>
                            </a>
                        </div>
                        <div class="layout-search">
                            <Input v-model="searchValue" icon="android-search" placeholder="Enter something..." @on-enter="search()"/>
                        </div>
                        <div v-if="loginFlag" class="layout-nav">
                            <MenuItem name="1">
                                {{user.loginName}}
                            </MenuItem>
                            <MenuItem name="2">
                                <Icon type="ios-email"></Icon>
                                邮件
                            </MenuItem>
                            <MenuItem name="3">
                                <Icon type="log-out"></Icon>
                                退出
                            </MenuItem>
                            <MenuItem name="4" v-if="consoleFlag">
                                <Icon type="gear-b"></Icon>
                                控制台
                            </MenuItem>
                        </div>
                        <img v-if="loginFlag" style="width: 30px;height: 30px;float: right; margin-top: 16px;border-radius: 100%;" :src="user.headimg">
                        <div v-if="!loginFlag" class="layout-nav">
                            <MenuItem name="5">
                                <Icon type="log-in"></Icon>
                                登录
                            </MenuItem>
                        </div>
                    </div>
                </Menu>
            </Header>
            <Content :style="{margin: '80px 0 0 0', background: '#fff'}">
                <!-- router-view 的使用必须要有子路由，比如index.vue下面关联了了home.vue(在router.js中的配置)-->
                <router-view></router-view>
            </Content>
            <Footer class="layout-footer-center">
                <div>
                    <a href="https://github.com/smallsnail-wh" target="_blank">
                        <Icon  style="color: rebeccapurple;" size="40" type="social-github"></Icon>
                    </a>
                </div>
                <p>2018-2020 &copy; testDemo</p>
            </Footer>
        </Layout>

        <Modal :mask-closable="false" :visible.sync="emailModal" :loading = "loading" v-model="emailModal" width="600" title="联系管理员" @on-ok="emailOk('email')" @on-cancel="cancel()">
             <Form ref="email" :rules="emailRule" :model="email"  :label-width="80" >
                <FormItem label="标题" prop="title">
                    <Input v-model="email.title" placeholder="请输入标题"/>
                </FormItem>
                <FormItem label="email" prop="email">
                    <Input v-model="email.email" placeholder="请输入email"/>
                </FormItem>
                <FormItem label="姓名" prop="name">
                    <Input v-model="email.name" placeholder="请输入姓名"/>
                </FormItem>
                <FormItem label="内容" prop="content">
                    <Input v-model="email.content" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."/>
                </FormItem>
            </Form>
        </Modal>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                //登录状态
                loginFlag: false,
                //控制台状态
                consoleFlag: false,
                loading: true,
                searchValue:'',
                emailModal:false,
                email: {
                    title:'',
                    email:'',
                    name:'',
                    content:''
                },
                user: {
                    loginName: '',
                    email: '',
                    headimg: ''
                },
                emailRule: {
                    title: [
                        { type:'string', required: true, message: '请输入密码', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '输入邮箱', trigger: 'blur' },
                        { type:'email', message: '输入正确的邮箱格式', trigger: 'blur' }
                    ],
                    passwordAgain: [
                        { type:'string', required: true, message: '请输入再次输入密码', trigger: 'blur' }
                    ],
                    name: [
                        { type:'string', required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ]
                    
                }
            }
        },
        mounted(){
            if (this.$store.getters._isMobile) {
                this.$router.replace('/mobile');
            }
            //取到第三方回调的code值，并且判断有值才发起后台请求,请求的格式为http://localhost:8088/?code=6d0d3691b95366f69312
            this.code = this.$route.query.code;
            if(this.code !=null && this.code != ''){
                //弹出遮罩层
                this.$Spin.show({
                    render: (h) => {
                        return h('div', [
                            h('Icon', {
                                style:{
                                    animation: 'ani-demo-spin 1s linear infinite'
                                },
                                props: {
                                    type: 'load-c',
                                    size: 18
                                }
                            }),
                            h('div', '正在登录，请等待...')
                        ])
                    }
                });
                setTimeout(() => {
                    this.$Spin.hide();
                }, 10000);
                this.axios({
                    method: 'post',
                    url: '/authentication/github',
                    params:{
                        "code": this.code
                    },
                    // 这将设置一个`Authorization'头，覆盖任何现有的`Authorization'自定义头，使用`headers`设置,
                    //所以可以看出这个请求中是没有currentUser_token，currentUser_refresh_token的属性的
                    auth: {
                        username: 'client',
                        password: 'secret'
                    }
                }).then(function (response) {
                    //成功就把token的值存入localStorage，其他请求要带着access_token去该系统的认证服务器获取资源
                    localStorage.setItem("currentUser_token",response.data.access_token);
                    localStorage.setItem("currentUser_refresh_token",response.data.refresh_token);
                    this.axios.defaults.headers.common['Authorization'] = 'bearer '+ localStorage.getItem("currentUser_token");
                    this.$router.push({ path: '/' }) ;
                    location.reload();
                }.bind(this)).catch(function (error) {
                    this.$Message.error('登陆失败');
                }.bind(this));
            }else{
                this.userGet();
            }
        },
        methods:{
            userGet(){
                this.axios({
                    method: 'get',
                    url: '/public/user'
                }).then(function (response) {
                    if(response.data != null && response.data != ''){
                        this.loginFlag = true;
                        this.userSet(response.data);
                        //管理员用户才有后台的操作权限
                        if(response.data.usertype == 1){
                            this.consoleFlag = true;
                        }
                    }
                }.bind(this)).catch(function (error) {
                    this.$Message.error('无权限');
                }.bind(this));
            },
            userSet(e){
                this.user.loginName = e.loginName;
                this.user.email = e.email;
                this.user.headimg = e.headimg;
            },
            search(){
                if(this.searchValue != null && this.searchValue != ''){
                    this.$router.push("/page/home/"+this.searchValue);
                }
            },
            menuSelect(e){
                if(e==1){
                }else if (e==2) {
                    this.emailModal = true;
                }else if (e==3) {
                    this.$store.dispatch('users/loginOUt',{"router":this.$router});
                }else if(e == 4){
                    this.$router.push("/base");
                }else if(e == 5){
                    //这里跳转到登录的url,对应router.js中的/login
                    this.$router.push("/login");
                }
            },
            backHome(){
                this.$router.push("/page/home");
            },
            cancel () {
                this.$Message.info('点击了取消');
            },
            emailOk(email){
                this.$refs[email].validate((valid) => {
                    if (valid) {
                        this.axios({
                          method: 'post',
                          url: '/email',
                          data: this.email
                        }).then(function (response) {
                            this.$Message.info('发送成功');
                        }.bind(this)).catch(function (error) {
                          alert(error);
                        });  
                        this.emailModal = false;
                    }else {
                        this.$Message.error('表单验证失败!');
                        setTimeout(() => {
                            this.loading = false;
                            this.$nextTick(() => {
                                this.loading = true;
                            });
                        }, 1000);
                    }
                })
            }
        }
    }
</script>