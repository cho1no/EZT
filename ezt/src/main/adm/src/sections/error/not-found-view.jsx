import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';

import { RouterLink } from 'src/routes/components';

// import Logo from 'src/components/logo';

// ----------------------------------------------------------------------

export default function NotFoundView() {
  // const renderHeader = (
  //   <Box
  //     component="header"
  //     sx={{
  //       top: 0,
  //       left: 0,
  //       width: 1,
  //       lineHeight: 0,
  //       position: 'fixed',
  //       p: (theme) => ({ xs: theme.spacing(3, 3, 0), sm: theme.spacing(5, 5, 0) }),
  //     }}
  //   >
  //     <Logo />
  //   </Box>
  // );

  return (
    <>
      {/* {renderHeader} */}

      <Container>
        <Box
          sx={{
            py: 0,
            maxWidth: 480,
            mx: 'auto',
            display: 'flex',
            minHeight: '100vh',
            textAlign: 'center',
            alignItems: 'center',
            flexDirection: 'column',
            justifyContent: 'center',
          }}
        >
          <Typography variant="h4" sx={{ mb: 3, fontWeight: 'bold' }}>
            페이지를 찾을 수 없습니다.
          </Typography>

          <Typography sx={{ color: 'text.secondary' }}>
            죄송합니다 페이지를 찾을 수 없습니다. 다른 경로를 이용해주세요.
          </Typography>

          <Box
            component="img"
            src="/assets/illustrations/illustration_404.svg"
            sx={{
              mx: 'auto',
              height: 260,
              my: { xs: 5, sm: 10 },
            }}
          />

          <Button
            href="http://localhost:8080/main"
            size="large"
            variant="contained"
            component={RouterLink}
          >
            사이트로 돌아가기
          </Button>
        </Box>
      </Container>
    </>
  );
}
