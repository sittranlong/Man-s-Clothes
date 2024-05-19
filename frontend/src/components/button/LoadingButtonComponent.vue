<script lang="tsx">
import {defineComponent, ref} from "vue";

export default defineComponent({
    name: 'LoadingButtonComponent',
    props: {
        text: {
            type: String,
            default: 'Loading'
        },
        loading: {
            type: Boolean,
            default: false
        },
        onclick: {
            type: Function,
            default: () => {
            }
        }
    },
    setup(props) {
        const text = ref(props.text);
        const loading = ref(props.loading);
        const on = () => {
            loading.value = true;
            props.onclick();
            loading.value = false;
        }
        return () => (
            <div>
                <button class="filled-button" type="button" disabled={loading.value} onClick={on}>
                    {loading.value && (
                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                    )}
                    {text.value}
                </button>
            </div>
        )
    }
})
</script>

<style scoped>
@import "style.css";
</style>