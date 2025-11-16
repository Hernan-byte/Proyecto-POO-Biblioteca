// dentro de tu frame

private JTable tablaUsuarios;
private JTextField txtId, txtNombre, txtEmail;
private JPasswordField txtPassword;
private JButton btnEditar, btnEliminar, btnNuevo;
private UsuarioService usuarioService;

public void inicializar() {
    usuarioService = new UsuarioService(new UsuarioRepositoryImpl());

    // cargar tabla
    cargarUsuariosEnTabla();

    // evento cuando seleccionan una fila
    tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila >= 0) {
            int id = (Integer) tablaUsuarios.getValueAt(fila, 0); // asumiendo que la primera columna es ID
            Usuario u = usuarioService.obtenerUsuarioPorId(id);
            txtId.setText(String.valueOf(u.getId()));
            txtNombre.setText(u.getNombre());
            txtEmail.setText(u.getEmail());
            txtPassword.setText(u.getPassword());
        }
    });

    btnEditar.addActionListener(e -> {
        Usuario u = new Usuario();
        u.setId(Integer.parseInt(txtId.getText()));
        u.setNombre(txtNombre.getText());
        u.setEmail(txtEmail.getText());
        u.setPassword(new String(txtPassword.getPassword()));
        usuarioService.actualizarUsuario(u);
        cargarUsuariosEnTabla();
        limpiarFormulario();
    });

    btnEliminar.addActionListener(e -> {
        int id = Integer.parseInt(txtId.getText());
        int confirmado = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmado == JOptionPane.YES_OPTION) {
            usuarioService.eliminarUsuario(id);
            cargarUsuariosEnTabla();
            limpiarFormulario();
        }
    });

    btnNuevo.addActionListener(e -> {
        limpiarFormulario();
    });
}

private void cargarUsuariosEnTabla() {
    List<Usuario> lista = usuarioService.obtenerTodosUsuarios();
    DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
    modelo.setRowCount(0);
    for (Usuario u : lista) {
        modelo.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getPassword()});
    }
}

private void limpiarFormulario() {
    txtId.setText("");
    txtNombre.setText("");
    txtEmail.setText("");
    txtPassword.setText("");
}
