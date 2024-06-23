<script lang="ts">
import {defineComponent, inject} from "vue";
import {Tour, TourGallery} from "@/base/model/tour.model";
import {TourService} from "@/base/service/tour.service";
import {toast} from "vue3-toastify";
import Editor from '@tinymce/tinymce-vue'
import type {Category} from "@/base/model/category.model";
import type {CategoryService} from "@/base/service/category.service";
import TourScheduleComponent from "@/components/admin/tour/TourScheduleComponent.vue";


//const TourScheduleComponent = defineAsyncComponent(() => import('@/components/admin/tour/TourScheduleComponent.vue'));
export default defineComponent({
    name: 'TourDetailComponent',
    components: {TourScheduleComponent, Editor},
    setup() {
        return {
            tourService: inject('tourService') as TourService,
            categoryService: inject('categoryService') as CategoryService
        }
    },
    data() {
        return {
            tour: new Tour(),
            id: '' as string,
            categoryList: [] as Category[],
            files: [] as File[],
            galleryFiles: [] as File[]
        }
    },
    methods: {
        back() {
            this.$router.push('/admin/tour');
        },
        loadCategoryList() {
            this.categoryService.findAll().then((data: Tour[]) => {
                this.categoryList = data;
            });
        },
        async submit() {
            if (this.files.length > 0) {
                const reader = new FileReader();
                // Create a Promise to handle the file reading operation
                const readFilePromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });
                reader.readAsDataURL(this.files[0]);

                try {
                    const avatarData = await readFilePromise;
                    this.tour.imageData = avatarData as string;
                    await this.updateOrInsert();
                } catch (error) {
                    toast.error('Error reading file');
                }
            } else {
                await this.updateOrInsert();
            }
        },
        async updateOrInsert() {
            await this.readGalleryFile();
            if (this.tour.id) {
                this.tour.tourScheduleList = this.$refs.tourSchedule?.tourScheduleList as Tour[];
                await this.tourService.update(this.tour);
                toast.success('Cập nhật thành công');
            } else {
                this.tour.tourScheduleList = this.$refs.tourSchedule?.tourScheduleList as Tour[];
                await this.tourService.save(this.tour);
                toast.success('Thêm mới thành công');
            }
            this.$router.push('/admin/tour');
        },
        async readGalleryFile() {

            for (let i = 0; i < this.galleryFiles.length; i++) {
                const reader = new FileReader();
                // Create a Promise to handle the file reading operation
                const readFilePromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });
                reader.readAsDataURL(this.galleryFiles[i]);
                try {
                    const avatarData = {
                        imageData: await readFilePromise,
                    };
                    this.tour.tourGalleryList?.push(avatarData as TourGallery);
                } catch (error) {
                    toast.error('Error reading file');
                }
            }
        }
    },
    created() {
        this.id = this.$route.params.id as string;
        if (this.id) {
            this.tourService.get(this.id).then((data) => {
                this.tour = data;
            });
        }
        this.loadCategoryList();
    }
})
</script>
<template>
    <div class="text-dark text-center">
        <div>
            <h1>Chi tiết tour</h1>
        </div>
        <div class="container">
            <v-form>
                <v-text-field v-model="tour.name" label="Tên"></v-text-field>
                <v-select v-model="tour.category"
                          :items="categoryList"
                          label="Danh mục"
                          item-title="name"
                          item-value="id"
                          return-object
                ></v-select>
                <v-text-field v-model="tour.location" label="Địa điểm"></v-text-field>
                <v-text-field v-model="tour.quantityForAdult" label="Số vé người lớn"></v-text-field>
                <v-text-field v-model="tour.quantityForChild" label="Số vé trẻ em"></v-text-field>
                <v-text-field v-model="tour.quantityForBaby" label="Số vé trẻ sơ sinh"></v-text-field>
                <v-text-field v-model="tour.priceAdult" label="Giá người lớn"></v-text-field>
                <v-text-field type="number" v-model="tour.priceChild" label="Giá trẻ em"></v-text-field>
                <v-text-field type="number" v-model="tour.priceBaby" label="Giá trẻ em"></v-text-field>
                <v-text-field type="datetime-local" v-model="tour.startDate" label="Ngày khởi hành"></v-text-field>
                <v-text-field v-model="tour.policy" label="Chính sách"></v-text-field>
                <v-text-field v-model="tour.vehicle" label="Phương tiện"></v-text-field>
                <v-text-field v-model="tour.departure" label="Địa điểm khởi hành"></v-text-field>
                <v-text-field v-model="tour.duration" label="Thời gian"></v-text-field>
                <tour-schedule-component class="d-flex" ref="tourSchedule"
                                         :tour-schedule-list-db="tour?.tourScheduleList"/>
                <v-file-input v-model="files" label="Ảnh đại diện"></v-file-input>
                <v-img v-if="tour.image" :src="'data:image/png;base64,' + tour.image" width="100" height="100"
                       :chips="true"></v-img>
                <v-file-input v-model="galleryFiles" label="Bộ sưu tập ảnh" :multiple="true"
                              :chips="true"></v-file-input>
                <v-row>
                    <v-col v-for="(item,index) in tour.tourGalleryList" :key="index" cols="4">
                        <v-img :src="'data:image/png;base64,' + item.image" width="100" height="100"></v-img>
                    </v-col>
                </v-row>
                <div>
                    <label class="d-flex">Tour này có gì hay</label>
                    <editor api-key="8t1et3v8to6ab5bhaxdaqf3bskllgzzm5ab34yemfs3i150g"
                            v-model="tour.attraction" :initial-value="tour.vehicle"></editor>
                </div>
                <div>
                    <label class="d-flex">Mô tả</label>
                    <editor api-key="8t1et3v8to6ab5bhaxdaqf3bskllgzzm5ab34yemfs3i150g"
                            v-model="tour.description" :initial-value="tour.description"></editor>
                </div>
                <div class="row">
                    <v-btn class="col" color="secondary" @click="back">Quay lại</v-btn>
                    <v-btn class="col" color="primary" @click="submit">Lưu</v-btn>
                </div>
            </v-form>
        </div>

    </div>
</template>
