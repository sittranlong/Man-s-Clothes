import {ProductService} from '@/base/service/product.service';
import {AuthService} from "@/base/service/auth.service";
import {UserService} from "@/base/service/user.service";
export const DependencyInjection = {
    install: (app: any,store : any) => {
        app.provide('productService', new ProductService(store))
        app.provide('authService', new AuthService(store))
        app.provide('userService', new UserService(store))
    }
};