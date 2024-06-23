<script lang="ts">
import {defineComponent, inject} from 'vue'
import {toast} from "vue3-toastify";
import {PostService} from "@/base/service/post.service";

export default defineComponent({
    name: 'PostComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index'},
            {title: 'Tiêu đề', value: 'title'},
            {title: 'Chi tiết bài viết', value: 'content'},
            {title: 'Hành động', value: 'action', sortable: false},
        ];
        return {
            headers,
            postService: inject('postService') as PostService
        }
    },
    data() {
        return {
            postPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                keyword: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.postService.findBySearch(this.searchParams).then((data) => {
                this.postPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.$router.push('/admin/post/' + item.id);
        },
        deletePost(item: any) {
            if (item) {
                if (confirm('Bạn có chắc chắn muốn xóa bài viết này ?')) {
                    this.postService.delete(item?.id).then(() => {
                        this.search();
                        toast.success('Xóa bài viết thành công');
                    });
                }
            }
        },
    },
    created() {
        this.search();
    }

})
</script>
<template>
    <div class="text-dark">
        <h1>Danh sách bài viết</h1>
        <v-text-field
            v-model="searchParams.keyword"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            hide-details
            @change="search"
            single-line
        ></v-text-field>
        <v-btn color="primary" @click="() => $router.push('/admin/post/new')">Thêm mới</v-btn>
        <v-data-table
            :headers="headers"
            :items="postPage.content"
            :loading="isLoading"
            :items-per-page="searchParams.size"
            :total-items="postPage.totalElements"
            :page="searchParams.page"
            :on-update:page="search"
            :on-update:items-per-page="search"
            :search="searchParams.keyword">
            <template v-slot:[`item.index`]="{index}">
                {{ index + 1 }}
            </template>
            <template v-slot:[`item.content`]="{item}">
                <p v-html="item?.content"></p>
            </template>
            <template v-slot:[`item.action`]="{item}">
                <td>
                    <v-icon left @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon left @click="deletePost(item)">mdi-delete</v-icon>
                </td>
            </template>
        </v-data-table>
    </div>
</template>
<style scoped>
</style>