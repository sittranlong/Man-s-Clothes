<script lang="ts">
import {defineComponent, inject, onMounted} from "vue";
import {AuthService} from "@/base/service/auth.service";
import {useStore} from "vuex";
import {toast} from "vue3-toastify";
import {useRouter} from "vue-router";

export default defineComponent({
    name: "LogoutPage",
    setup() {
        const store = useStore();
        const router = useRouter();
        const authService = inject('authService') as AuthService;
        onMounted(() => {
            authService.logout().then(() => {
                store.dispatch('logout');
                toast.success('Đăng xuất thành công');
                router.push('/');
            }).catch(() => {
                toast.error('Đăng xuất thất bại');
            });
        });
    }
});
</script>