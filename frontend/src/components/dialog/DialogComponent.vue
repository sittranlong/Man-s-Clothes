<script lang="ts">
import {defineComponent, ref} from "vue";

export default defineComponent({
    name: 'DialogComponent',
    props: {
        show: {
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: ''
        },
        message: {
            type: String,
            default: ''
        },
        confirm: {
            type: Function,
            default: () => {
            }
        },
        cancel: {
            type: Function,
            default: () => {
            }
        }
    },
})
</script>