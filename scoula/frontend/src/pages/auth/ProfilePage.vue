<script setup>
import authApi from '@/api/authApi';
import { useAuthStore } from '@/stores/auth';
import { computed, reactive, ref } from 'vue';

const auth = useAuthStore();

const avatar = ref(null);
// const avatarPath = `/api/member/${auth.username}/avatar`;

// (5) avatarPath 값을 스토어의 avatarUrl로 변경
const avatarPath = computed(() => auth.avatarUrl);

const member = reactive({
  username: auth.username,
  email: auth.email,
  password: '',
  avatar: null,
});

const error = ref('');

const disableSubmit = computed(() => !member.email || !member.password);
const onSubmit = async () => {
  if (!confirm('수정하시겠습니까?')) return;

  if (avatar.value.files.length > 0) {
    member.avatar = avatar.value.files[0];
  }

  try {
    // 3.
    await authApi.update(member);

    // 4. 성공 시 상태 동기화
    error.value = '';
    auth.changeProfile(member);
    alert('정보를 수정하였습니다.');
    member.password = '';

    if (member.avatar) {
      auth.updateAvatar();
    }
  } catch (e) {
    error.value = e.response.data;
  }
};
</script>
<template>
  <div class="mt-5 mx-auto" style="width: 500px">
    <h1><i class="fa-solid fa-user-gear my-3"></i> 회원 정보</h1>
    <form @submit.prevent="onSubmit">
      <div class="mb-3 mt-3">
        <img :src="avatarPath" class="avatar avatar-lg me-4" />
        {{ member.username }}
      </div>
      <div class="mb-3 mt-3">
        <label for="avatar" class="form-label">
          <i class="fa-solid fa-user-astronaut"></i>
          아바타 이미지:
        </label>
        <input
          type="file"
          class="form-control"
          ref="avatar"
          id="avatar"
          accept="image/png, image/jpeg"
        />
      </div>
      <div class="mb-3 mt-3">
        <label for="email" class="form-label">
          <i class="fa-solid fa-envelope"></i>
          email
        </label>
        <input
          type="email"
          class="form-control"
          placeholder="Email"
          id="email"
          v-model="member.email"
        />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          비밀번호:
        </label>
        <input
          type="password"
          class="form-control"
          placeholder="비밀번호"
          id="password"
          v-model="member.password"
        />
      </div>
      <div v-if="error" class="text-danger">{{ error }}</div>
      <div class="text-center">
        <button
          type="submit"
          class="btn btn-primary mt-4 me-3"
          :disabled="disableSubmit"
        >
          <i class="fa-solid fa-user-plus"></i>
          확인
        </button>
        <router-link class="btn btn-primary mt-4" to="/auth/changepassword">
          <i class="fa-solid fa-lock"></i>
          비밀번호 변경
        </router-link>
      </div>
    </form>
  </div>
</template>
