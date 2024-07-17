import { lazy, Suspense } from 'react';
import { Outlet, Navigate, useRoutes } from 'react-router-dom';

// eslint-disable-next-line import/no-cycle
import DashboardLayout from 'src/layouts/dashboard';

export const IndexPage = lazy(() => import('../pages/app'));
export const BlogPage = lazy(() => import('../pages/blog'));
export const UserPage = lazy(() => import('../pages/user'));
export const CareerPage = lazy(() => import('../pages/career'));
export const ContractPage = lazy(() => import('../pages/contract'));
export const PayPage = lazy(() => import('../pages/payment'));
export const LoginPage = lazy(() => import('../pages/login'));
export const RequestPage = lazy(() => import('../pages/request'));
export const ProductsPage = lazy(() => import('../pages/products'));
export const Page404 = lazy(() => import('../pages/page-not-found'));

// ----------------------------------------------------------------------

export default function Router() {
  const routes = useRoutes([
    {
      element: (
        <DashboardLayout>
          <Suspense>
            <Outlet />
          </Suspense>
        </DashboardLayout>
      ),
      children: [
        { element: <IndexPage />, path: 'admin' },
        { path: 'admin/user', element: <UserPage /> },
        { path: 'admin/career', element: <CareerPage /> },
        { path: 'admin/contract', element: <ContractPage /> },
        { path: 'admin/payment', element: <PayPage /> },
        { path: 'admin/request', element: <RequestPage /> },
        { path: 'admin/products', element: <ProductsPage /> },
        { path: 'admin/blog', element: <BlogPage /> },
      ],
    },
    {
      path: 'login',
      element: <LoginPage />,
    },
    {
      path: 'admin/404',
      element: <Page404 />,
    },
    {
      path: '*',
      element: <Navigate to="/admin/404" replace />,
    },
  ]);

  return routes;
}
