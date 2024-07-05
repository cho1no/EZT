// styles
export const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '40%',
  height: '80%',
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 3,
};
export const contentStyle = {
  position: 'relative',
  overflowY: 'auto',
  height: '90%',
  paddingRight: 2,
  '&::-webkit-scrollbar': {
    width: '5px',
  },
  '&::-webkit-scrollbar-thumb': {
    background: '#888',
    borderRadius: '10px',
  },
};
export const boxStyle = {
  border: '1px solid #ccc',
  borderRadius: '8px',
  padding: '8px 12px',
  marginTop: '3px',
};
export const textareaStyle = {
  width: '100%',
  resize: 'none',
  outline: 'none',
  border: '1px solid rgba(0, 0, 0, 0.1)',
  borderRadius: '8px',
  padding: '16.5px 14px',
  overflow: 'auto',
};
