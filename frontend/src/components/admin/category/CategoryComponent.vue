<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {CategoryService} from "@/base/service/category.service";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'CategoryComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tên danh mục', value: 'name'},
            {title: 'Mô tả', value: 'description'},
            {title: 'Hành động', value: 'action'}
        ];
        return {
            headers,
            categoryService: inject('categoryService') as CategoryService,
            router : useRouter()
        }
    },
    data() {
        return {
            categoryPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 1,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.categoryService.findBySearch(this.searchParams).then((data) => {
                this.categoryPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.router.push('/admin/category/' + item.id);
        },
        deleteCategory(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa danh mục này ?')) {
                    this.categoryService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa danh mục thành công');
                    });
                }
            }
        }
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách danh mục</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => router.push('/admin/category/new')">Thêm mới</v-btn>
        <v-data-table-server
            :headers="headers"
            :items="categoryPage.content"
            :loading="isLoading"
            v-model:items-per-page="searchParams.size"
            v-model:page="searchParams.page"
            :items-length="categoryPage.totalElements"
            :items-per-page="searchParams.size"
            @update:itemsPerPage="search"
            @update:page="search"
            :search="searchParams.keyword">
            <template v-slot:[`item.index`]="{index}">
                <td>{{index + 1}}</td>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deleteCategory(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table-server>
    </div>
</template>
<style scoped>
</style>