import {ProductService} from '@/base/service/product.service';

export const DependencyInjection = {
    install: (app: any) => {
        app.provide('productService', new ProductService());
    }
};