import {ProductService} from '@/base/service/product.service';
import {AuthService} from "@/base/service/auth.service";
import {UserService} from "@/base/service/user.service";
import {CategoryService} from "@/base/service/category.service";
import {BrandService} from "@/base/service/brand.service";
export const DependencyInjection = {
    install: (app: any,store : any) => {
        app.provide('productService', new ProductService(store))
        app.provide('authService', new AuthService(store))
        app.provide('userService', new UserService(store))
        app.provide('categoryService', new CategoryService(store))
        app.provide('brandService', new BrandService(store))
    }
};