import { Helmet } from 'react-helmet-async';

import { ContractView } from 'src/sections/unity-contract/view/';

// ----------------------------------------------------------------------

export default function ContractPage() {
  return (
    <>
      <Helmet>
        <title> 통일 계약서 관리 </title>
      </Helmet>

      <ContractView />
    </>
  );
}
