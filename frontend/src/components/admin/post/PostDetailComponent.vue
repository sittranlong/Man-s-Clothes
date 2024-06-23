<script lang="ts">
import {defineComponent, inject} from "vue";
import {Post} from "@/base/model/post.model";
import {PostService} from "@/base/service/post.service";
import {toast} from "vue3-toastify";
import Editor from "@tinymce/tinymce-vue";

export default defineComponent({
    name: 'PostDetailComponent',
    components: {Editor},
    setup() {
        return {
            postService: inject('postService') as PostService
        }
    },
    data() {
        return {
            post: new Post(),
            id: '' as string,
        }
    },
    methods: {
        back() {
            this.$router.push('/admin/post');
        },
        submit() {
            if (this.post.id) {
                this.postService.update(this.post).then(() => {
                    this.$router.push('/admin/post');
                    toast.success('Cập nhật thành công');
                });
            } else {
                this.postService.save(this.post).then(() => {
                    this.$router.push('/admin/post');
                    toast.success('Thêm mới thành công');
                });
            }
        }
    },
    created() {
        this.id = this.$route.params.id as string;
        this.postService.get(this.id).then((data: Post) => {
            this.post = data;
        });
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
                <v-text-field v-model="post.title" label="Tên bài viết"></v-text-field>
                <editor
                    api-key="8t1et3v8to6ab5bhaxdaqf3bskllgzzm5ab34yemfs3i150g"
                    v-model="post.content"
                    :initial-value="post.content"
                ></editor>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
