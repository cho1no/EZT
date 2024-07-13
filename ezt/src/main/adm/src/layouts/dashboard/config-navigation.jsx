import SvgColor from 'src/components/svg-color';

// ----------------------------------------------------------------------

const icon = (name) => (
  <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />
);

const navConfig = [
  {
    title: '대시보드',
    path: '/',
    icon: icon('ic_analytics'),
  },
  {
    title: '사용자 관리',
    path: '/user',
    icon: icon('ic_user'),
  },
  {
    title: '통일 계약서 관리',
    path: '/contract',
    icon: icon('ic_file'),
  },
  {
    title: '경력 인증 관리',
    path: '/career',
    icon: icon('ic_userCheck'),
  },
  {
    title: '결제 관리',
    path: '/payment',
    icon: icon('ic_card'),
  },
  {
    title: '의뢰 관리',
    path: '/request',
    icon: icon('ic_data'),
  },
];

export default navConfig;
