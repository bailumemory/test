import vue from 'vue'
import VueRouter from 'vue-router'


vue.use(VueRouter)

export const routes=[
    {
        path:'/layout',
        name:'layout',
        component:() => import("@/layout/layout"),
        redirect:"/user",
        children:[
            {
                path:'/user',
                name:'User',
                component:() => import("@/components/User")
            },
            {
                path:'/product',
                name:'Product',
                component:() => import("@/components/Product")
            },
            {
                path:'/productType',
                name:'product_type',
                component:() => import("@/components/product_type")
            },
            {
                path:'/order',
                name:'order',
                component:() =>import("@/components/Order")
            }
            ]
    },
    {
        path:'/login',
        name:'login',
        component:() => import("@/components/login")
    },
    {
        path:'/register',
        name:'register',
        component:() => import("@/components/register")
    },
    {
      path:'/Shoppingcar',
      name:'shoppingcar',
      component: () => import("@/components/ShoppingCar")
    },
    {
        path:'/daohang',
        name:'daohang',
        component:()=> import("@/components/daohang")
    },
    {
        path:'/OrderForm',
        name:'orderForm',
        component:() =>import("@/components/OrderForm")
    },
    {
        path:'/Personal_information',
        name:'Personal_information',
        component:() =>import("@/components/Personal_information")
    },
    {
        path:'/front-end',
        name:'front-end',
        component: () => import("@/layout/front-end"),
        redirect: "/shopping-info",
        children: [
            {
                path:"/shopping-Info",
                name:"Shopping-Info",
                component:() => import("@/components/Shopping-Info")
            }
        ]
    }
]

const router=new VueRouter({
    routes
    }
)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
export  default router




