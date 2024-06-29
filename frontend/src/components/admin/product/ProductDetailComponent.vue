<script lang="ts">
import {defineComponent, inject} from "vue";
import {Product} from "@/base/model/product.model";
import {ProductService} from "@/base/service/product.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";
import {CategoryService} from "@/base/service/category.service";
import {BrandService} from "@/base/service/brand.service";
import {Brand} from "@/base/model/brand.model";
import {Category} from "@/base/model/category.model";

export default defineComponent({
    name: 'ProductDetailComponent',
    setup() {
        return {
            productService: inject('productService') as ProductService,
            categoryService: inject('categoryService') as CategoryService,
            brandService: inject('brandService') as BrandService,
            router: useRouter(),
            route: useRoute()
        }
    },
    data() {
        return {
            product: new Product(),
            id: '' as string,
            productList: [] as Product[],
            brandList: [] as Brand[],
            categoryList: [] as Category[],
            image :[] as File[]
        }
    },
    methods: {
        back() {
            this.router.push('/admin/product');
        },
        async submit() {
            if (this.product.id) {
                if (this.image.length > 0) {
                    const reader = new FileReader();
                    const readerPromise = new Promise((resolve, reject) => {
                        reader.onload = () => {
                            resolve(reader.result);
                        };
                        reader.onerror = (error) => {
                            reject(error);
                        };
                    });
                    reader.readAsDataURL(this.image[0]);
                    try {
                        const imageData = await readerPromise;
                        this.product.imageBase64 = imageData as string;
                    } catch (error) {
                        toast.error('Error reading file');
                    }
                }
                this.productService.update(this.id, this.product).then(() => {
                    this.router.push('/admin/product');
                    toast.success('Cập nhật thành công');
                });
            } else {
                if (this.image.length > 0) {
                    const reader = new FileReader();
                    const readerPromise = new Promise((resolve, reject) => {
                        reader.onload = () => {
                            resolve(reader.result);
                        };
                        reader.onerror = (error) => {
                            reject(error);
                        };
                    });
                    reader.readAsDataURL(this.image[0]);
                    try {
                        const imageData = await readerPromise;
                        this.product.imageBase64 = imageData as string;
                    } catch (error) {
                        toast.error('Error reading file');
                    }
                }
                this.productService.save(this.product).then(() => {
                    this.router.push('/admin/product');
                    toast.success('Thêm mới thành công');
                });
            }
        },
        async loadCategoryList() {
            this.categoryList = await this.categoryService.findAll();
        },
        async loadBrandList() {
            this.brandList = await this.brandService.findAll();
        },
        clickImage() {
            document.getElementById('image')?.click();
        }
    },
    created() {
        this.id = this.route?.params.id as string;
        this.productService.getById(this.id).then((data: Product) => {
            this.product = data;
        });
        this.loadCategoryList();
        this.loadBrandList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết sản phẩm</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field v-model="product.name" label="Tên sản phẩm" class="col"
                              :rules="[v => !!v || 'Tên sản phẩm không được để trống']"></v-text-field>
                <v-text-field v-model="product.description" label="Mô tả" class="col"
                              :rules="[v => !!v || 'Mô tả không được để trống']"></v-text-field>
                <v-text-field v-model="product.price" label="Giá" class="col"
                              :rules="[v => !!v || 'Giá không được để trống']"></v-text-field>
                <v-text-field v-model="product.discountPercent" label="Giảm giá" class="col"
                              :rules="[v => !!v || 'Giảm giá không được để trống']"></v-text-field>
                <v-select v-model="product.categoryId" :items="categoryList" label="Danh mục" class="col" item-title="name"
                          item-value="id"></v-select>
                <v-select v-model="product.brandId" :items="brandList" label="Thương hiệu" class="col" item-title="name"
                            item-value="id"></v-select>
                <v-file-input id="image" v-show="!product.image" v-model="image" label="Ảnh" class="col"></v-file-input>
                <div class="col" v-show="product.image">
                    <v-img role="button" @click="clickImage" :src="'data:image/png;base64,'+ product.image" width="100" height="100"></v-img>
                </div>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
