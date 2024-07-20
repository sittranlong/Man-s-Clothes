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
import ProductDetailDialogComponent from "@/components/admin/product/ProductDetailDialogComponent.vue";
import Editor from "@tinymce/tinymce-vue";
export default defineComponent({
    name: 'ProductDetailComponent',
    components: {ProductDetailDialogComponent, Editor},
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
            this.product.productDetails = this.$refs?.productDetail?.productDetails || [];
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
        loadCategoryList() {
            this.categoryService.findAll().then((data: Category[]) => {
                this.categoryList = data;
            });
        },
        loadBrandList() {
            this.brandService.findAll().then((data: Brand[]) => {
                this.brandList = data;
            });
        },
        clickImage() {
            document.getElementById('image')?.click();
        },
        async getProductDetail() {
            if (this.id !== 'new') {
                this.productService.getById(this.id).then((data: Product) => {
                    this.product = data;
                });
            }
        }
    },
    created() {
        this.id = this.route?.params.id as string;
        this.getProductDetail();
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
            <v-form class="container">
                <v-text-field v-model="product.name" label="Tên sản phẩm" class="col"
                              :rules="[v => !!v || 'Tên sản phẩm không được để trống']"></v-text-field>
                <v-text-field type="number" v-model="product.price" label="Giá" class="col"
                              :rules="[v => !!v || 'Giá không được để trống']"></v-text-field>
                <v-text-field type="number" v-model="product.discountPercent" label="Giảm giá" class="col"></v-text-field>
                <v-select v-model="product.categoryId" :items="categoryList" label="Danh mục" class="col" item-title="name"
                          item-value="id"></v-select>
                <v-select v-model="product.brandId" :items="brandList" label="Thương hiệu" class="col" item-title="name"
                          item-value="id"></v-select>
                <v-file-input id="image" v-show="!product.image" v-model="image" label="Ảnh" class="col"></v-file-input>
                <div class="col" v-show="product.image">
                    <v-img role="button" @click="clickImage" :src="'data:image/png;base64,'+ product.image" width="100"
                           height="100"></v-img>
                </div>
                <ProductDetailDialogComponent class="mb-3" :key="product.id" ref="productDetail" :product="product"></ProductDetailDialogComponent>
                <editor v-model="product.description"
                        api-key="haqb2cfh7d0ungglu7xiok8bqoxo0qm57s5fzmmmxyhiymif"
                        :init="{
                    height: 500,
                    menubar: false,
                    plugins: [
                        'advlist autolink lists link image charmap print preview anchor',
                        'searchreplace visualblocks code fullscreen',
                        'insertdatetime media table paste code help wordcount'
                    ],
                    toolbar:
                        'undo redo | formatselect | bold italic backcolor | \
                        alignleft aligncenter alignright alignjustify | \
                        bullist numlist outdent indent | removeformat | help'
                }"></editor>
                <div class="row my-3">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>
    </div>
</template>

<style scoped>
.container {
    max-width: 80%;
}
</style>
