<script lang="ts">
import {defineComponent, inject} from "vue";
import {Category} from "@/base/model/category.model";
import {CategoryService} from "@/base/service/category.service";
import {toast} from "vue3-toastify";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'CategoryDetailComponent',
    setup() {
        return {
            categoryService: inject('categoryService') as CategoryService,
            router : useRouter(),
            route : useRoute()
        }
    },
    data() {
        return {
            category: new Category(),
            id: '' as string,
            categoryList: [] as Category[]
        }
    },
    methods: {
        back() {
            this.router.push('/admin/category');
        },
        loadCategoryList() {
            this.categoryService.findAll().then((data: Category[]) => {
                this.categoryList = data;
            });
        },
        submit() {
            if (this.category.id) {
                this.categoryService.update(this.category).then(() => {
                    this.router.push('/admin/category');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.categoryService.save(this.category).then(() => {
                    this.router.push('/admin/category');
                    toast.success('Thêm mới thành công');
                });
            }
        }
    },
    created() {
        this.id = this.route?.params.id as string;
        this.categoryService.get(this.id).then((data: Category) => {
            this.category = data;
        });
        this.loadCategoryList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết danh mục</h1>
        </div>
        <div class="d-flex justify-content-center">
            <v-form class="container row col-6">
                <v-text-field v-model="category.name" label="Tên danh mục"></v-text-field>
                <v-select v-model="category.parentId"
                          :items="categoryList"
                          label="Danh mục cha"
                          item-title="name"
                          item-value="id"
                ></v-select>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
