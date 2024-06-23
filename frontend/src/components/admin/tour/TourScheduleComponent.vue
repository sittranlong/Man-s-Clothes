<script lang="ts">
import { TourSchedule } from "@/base/model/tour.model";
import { toast } from "vue3-toastify";
import Editor from "@tinymce/tinymce-vue";
import { defineComponent, watch } from "vue";

export default defineComponent({
    name: 'TourScheduleComponent',
    components: { Editor },
    props: {
        tourScheduleListDb: {
            type: Array,
            default: () => [] as TourSchedule[],
        },
    },
    data() {
        return {
            tourScheduleList: [] as TourSchedule[],
            dialog: false,
            files: [] as File[]
        }
    },
    methods: {
        closeDialog() {
            this.dialog = false;
        },
        async uploadFile(index: number) {
            if (this.files.length > 0) {
                const reader = new FileReader();
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
                    this.tourScheduleList[index].imageData = avatarData as string;
                } catch (error) {
                    toast.error('Error reading file');
                } finally {
                    this.files = [];
                }
            }
        },
        initializeTourScheduleList() {
            if (this.tourScheduleListDb.length > 0) {
                this.tourScheduleList = this.tourScheduleListDb.map(schedule => ({ ...schedule } as TourSchedule));
            } else {
                this.tourScheduleList = [new TourSchedule()];
            }
        },
        addSchedule() {
            this.tourScheduleList.push(new TourSchedule());
        }
    },
    created() {
        this.initializeTourScheduleList();
    },
    watch: {
        tourScheduleListDb: {
            handler(newVal) {
                this.initializeTourScheduleList();
            },
            immediate: true
        }
    }
});
</script>
<template>
    <v-container>
        <!-- Nút để mở dialog -->
        <v-btn color="primary" @click="dialog = true">Thêm lịch trình</v-btn>

        <!-- Dialog để hiển thị form -->
        <v-dialog v-model="dialog" :persistent="true" max-width="1200px">
            <v-card>
                <v-card-title>
                    <span class="headline">Lịch trình</span>
                </v-card-title>
                <v-card-text>
                    <v-form>
                        <v-row v-for="(item, i) in tourScheduleList" :key="i">
                            <v-col cols="12" md="6">
                                <v-text-field
                                    v-model="item.name"
                                    label="Tiêu đề lịch trình"
                                    required
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12" md="6">
                                <v-file-input v-if="item.image?.length === 0"
                                    v-model="files"
                                    @change="uploadFile(i)"
                                    label="Ảnh minh họa"
                                ></v-file-input>
                                <v-img v-else :src="'data:image/png;base64,' + item.image" width="100" height="100"></v-img>
                            </v-col>
                            <v-col cols="12" md="6">
                                <label class="d-flex">Mô tả</label>
                                <editor
                                    api-key="8t1et3v8to6ab5bhaxdaqf3bskllgzzm5ab34yemfs3i150g"
                                    v-model="item.description"
                                    :initial-value="item.description"
                                ></editor>
                            </v-col>
                            <hr/>
                        </v-row>
                        <v-btn @click="addSchedule">Thêm lịch</v-btn>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" @click="closeDialog">Cancel</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>
